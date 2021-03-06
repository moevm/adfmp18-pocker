package game.poker.core.handle

import com.google.gson.JsonObject
import game.poker.Settings
import game.poker.core.*
import game.poker.screens.TableScreen
import game.poker.staticFiles.Sounds
import game.poker.staticFiles.Texts
import game.poker.staticFiles.Texts.TextKeys
import java.util.*


class GameHandler(conn: WebSocketConnection,
                  queue: Queue<String>,
                  table: TableScreen) : Handler(conn, table, queue) {

    var resitMode = false
    var inGame = false
    val premoves = Premoves(table)
    val raises = Raises(this)

    init {
        gameMode = true
    }

    override fun open(){
        val json = JsonObject()
        json.addProperty("type", "js")
        json.addProperty("name", Settings.nick)
        json.addProperty("id", Settings.currTournamentId)
        json.addProperty("password", "")
        socket.connectToServer(json.toString())
        info.waitWhileAllPlayersRegister()
    }

    fun setPremove(ans: Int, isChecked: Boolean){
        premoves.set(ans, isChecked)
    }

    private fun sendDecision(toSend: String){
        table.currView.removePlayingChoices()
        val json = JsonObject()
        json.addProperty("type", "decision")
        json.addProperty("text", toSend)
        socket.send(json.toString())
    }

    fun sendFold(){
        sendDecision("1")
    }

    fun sendMiddle(){
        sendDecision("2")
    }

    fun sendRaise(amount: Long){
        val raiseValue: Long = amount + seats.me.gived
        sendDecision("3 " + raiseValue.toString())
    }

    override fun reconnectEnd(data: JsonObject){
        if(!resitMode){
            info.successReconnection()
        }
        resitMode = false
        super.reconnectEnd(data)
    }

    override fun initHand(data: JsonObject) {
        if(waitForInit){
            info.startedGame()
        }
        super.initHand(data)
    }

    override fun blinds(data: JsonObject) {
        if(!resitMode){
            inGame = true

            val info = data["info"].asJsonArray
            val index = if(info.size() == 1) 0 else 1

            val currBB = info[index].asJsonObject["paid"].asLong
            val currBBId = info[index].asJsonObject["id"].asInt

            if(currBBId == seats.myId){
                if(currBB < seats.me.stack){
                    premoves.checkFold()
                }
                else{
                    // so I am in all-in and premoves should be hidden
                    premoves.hide()
                }
            }
            else{
                if(seats.bb < seats.me.allMoney()){
                    premoves.callFold(seats.bb - seats.me.gived)
                }
                else{
                    // big blind amount bigger than my stack so either fold or all-in
                    premoves.allInFold(seats.me.stack)
                }
            }
        }
        super.blinds(data)
    }

    override fun giveCards(data: JsonObject) {
        table.currView.dealCards()

        val card1 = Card.fromString(data["first"].asString)
        val card2 = Card.fromString(data["second"].asString)

        table.currView.setPlayerCards(seats.me.localSeat, card1, card2)
    }

    override fun resit(data: JsonObject) {
        resitMode = true

        val tableNumber = data["table_number"].asLong.insertSpaces()
        val isFinal = data["is_final"].asBoolean

        info.resit(tableNumber, isFinal)

        if(isFinal){
            table.currView.setTableNum(Texts[TextKeys.FINAL_TABLE])
            table.isFinal = true
        }
        else{
            table.currView.setTableNum("${Texts[TextKeys.TABLE]} #$tableNumber")
        }

        //seats = Seats(table, data, gameMode)
    }

    override fun madeDecision(data: JsonObject) {
        if(seats.idInDecision == seats.myId){
            when(data["result"].asString){
                "fold" -> {
                    inGame = false
                    premoves.hide()
                }
                "check", "raise" -> {
                    premoves.reset()
                    premoves.checkFold()
                }
                "call" -> {
                    if(seats.me.allMoney() == data["money"].asLong){
                        premoves.hide()
                    }
                    else{
                        premoves.reset()
                        premoves.checkFold()
                    }
                }
                "all in" -> {
                    premoves.hide()
                }
            }
        }
        else if(inGame){
            when(data["result"].asString){
                "raise", "all in" -> {
                    if(premoves.second){
                        // fold stays at fold, 'call any' stays at 'call any' or moved to 'all in'
                        // only 'call' resets
                        premoves.reset()
                    }

                    if(seats.me.allMoney() <= data["money"].asLong){
                        premoves.allInFold(seats.me.stack)
                    }
                    else{
                        premoves.callFold(data["money"].asLong - seats.me.gived)
                    }
                }
            }
        }
        super.madeDecision(data)
    }

    override fun flop(data: JsonObject) {
        premoves.reset()
        super.flop(data)
    }

    override fun turn(data: JsonObject) {
        premoves.reset()
        super.turn(data)
    }

    override fun river(data: JsonObject) {
        premoves.reset()
        super.river(data)
    }

    override fun openCards(data: JsonObject) {
        premoves.hide()
        inGame = false
        super.openCards(data)
    }

    override fun giveMoney(data: JsonObject) {
        premoves.hide()
        super.giveMoney(data)
    }

    override fun handResults(data: JsonObject) {
        //TODO()
        super.handResults(data)
    }

    override fun busted(data: JsonObject) {
        socket.clean = true
        info.busted(data["place"].asInt)
        inLoop = false
    }

    override fun win(data: JsonObject) {
        socket.clean = true
        info.win()
        inLoop = false
    }

    override fun place(data: JsonObject) {
        if(!reconnectMode){
            table.currView.setPlaceInfo(data["place"].asString,
                    seats.playersLeft.toLong().insertSpaces())
        }
    }

    override fun kick(data: JsonObject) {
        socket.clean = true
        info.kick()
        table.currView.removeDecisions()
        inLoop = false
    }

    override fun setDecision(data: JsonObject) {
        // if it in reconnect mode then player was autofolded
        if(reconnectMode){
            return
        }

        val decisions = data["decisions"].asJsonArray

        // all premoves checking flags stays in 'this.made_decision' method
        if(premoves.first){
            if(decisions[1].asJsonObject["type"].asString == "check"){
                sendDecision("2") // if 'check/fold' then autoclick 'check'
                // but 'check/fold' flag stays so if someone raises then autoclick 'fold'
                return
            }
            else{
                // autoclick 'fold'
                sendDecision("1")
                return
            }
        }
        else if(premoves.second){
            // autoclick 'call'
            sendDecision("2")
            return
        }
        else if(this.premoves.third){
            // because third is 'call any' so autoclick 'call'
            sendDecision("2")
            return
        }

        premoves.hide()

        playSound(Sounds.SoundType.ATTENTION)

        val choicesToShow = mutableListOf<String>()

        for(decision in decisions) {
            val curr = decision.asJsonObject
            val type = curr["type"].asString

            when (type) {
                "fold" -> {
                    choicesToShow.add(Texts[TextKeys.FOLD])
                }
                "check" -> {
                    choicesToShow.add(Texts[TextKeys.CHECK])
                }
                "call" -> {
                    val money = curr["money"].asLong
                    val needToAdd = money - seats.me.gived
                    choicesToShow.add("${Texts[TextKeys.CALL]} $needToAdd")
                    seats.toCall = money
                }
                "raise" -> {
                    choicesToShow.add(Texts[TextKeys.RAISE])

                    val minVal = curr["from"].asLong
                    val maxVal = curr["to"].asLong
                    val step = minVal

                    var inPot = seats.mainChips

                    for(seat in seats.all()){
                        inPot += seat.gived
                    }

                    var potAmount = inPot + 2 * seats.toCall

                    if(potAmount > maxVal){
                        potAmount = maxVal
                    }

                    table.currView.setRaiseInfo(minVal - seats.me.gived,
                            maxVal - seats.me.gived, step, potAmount)
                }
                "all in" -> {
                    val money = curr["money"].asLong
                    val needToAdd = money - seats.me.gived
                    table.currView.setRaiseInfo(needToAdd, needToAdd, 0, 0)
                    choicesToShow.add(Texts[TextKeys.ALL_IN])
                }
            }

            table.currView.setPlayingChoices(choicesToShow)
        }
    }

}