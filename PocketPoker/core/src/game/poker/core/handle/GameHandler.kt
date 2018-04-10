package game.poker.core.handle

import com.google.gson.JsonObject
import game.poker.Settings
import game.poker.core.*
import game.poker.screens.TableScreen
import java.util.*


class GameHandler(val name: String,
                  conn: WebSocketConnection,
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
        json.addProperty("name", name)
        TODO("Добавить поле ID из настроек")
        socket.connectToServer(json.toString())
        info.waitWhileAllPlayersRegister()
    }

    fun setPremove(ans: Int, isChecked: Boolean){
        premoves.set(ans, isChecked)
    }

    fun sendDecision(toSend: String){
        table.currView.removePlayingChoices()
        val json = JsonObject()
        json.addProperty("type", "decision")
        json.addProperty("text", toSend)
        socket.send(json.toString())
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
        info.resit()

        val tableNumber = data["table_number"].asLong.insertSpaces()

        if(data["is_final"].asBoolean){
            table.currView.setTableNum(Settings.getText(Settings.TextKeys.FINAL_TABLE))
            table.isFinal = true
        }
        else{
            table.currView.setTableNum("${Settings.getText(Settings.TextKeys.TABLE)} #$tableNumber")
        }

        seats = Seats(table, data, gameMode)
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
        TODO()
        super.handResults(data)
    }

    override fun busted(data: JsonObject) {
        socket.clean = true
        info.busted()
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
        info.kick();
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

        val choicesToShow = mutableListOf<String>()

        for(decision in decisions) {
            val curr = decision.asJsonObject
            val type = curr["type"].asString

            when (type) {
                "fold" -> {
                    choicesToShow.add(Settings.getText(Settings.TextKeys.FOLD))
                }
                "check" -> {
                    choicesToShow.add(Settings.getText(Settings.TextKeys.CHECK))
                }
                "call" -> {
                    val money = curr["money"].asLong
                    val needToAdd = money - seats.me.gived
                    choicesToShow.add("${Settings.getText(Settings.TextKeys.CALL)} $needToAdd")
                    seats.toCall = money
                }
                "raise" -> {
                    choicesToShow.add(Settings.getText(Settings.TextKeys.RAISE))

                    val minVal = curr["from"].asLong
                    val maxVal = curr["to"].asLong
                    val step = minVal

                    var inPot = seats.mainChips;

                    for(seat in seats.all()){
                        inPot += seat.gived
                    }

                    var potAmount = inPot + 2 * seats.toCall

                    if(potAmount > maxVal){
                        potAmount = maxVal
                    }

                    table.currView.setRaiseInfo(minVal, maxVal, step, potAmount)
                }
                "all in" -> {
                    val money = curr["money"].asLong
                    val needToAdd = money - seats.me.gived
                    choicesToShow.add("${Settings.getText(Settings.TextKeys.ALL_IN)} $needToAdd")
                }
            }

            table.currView.setPlayingChoices(choicesToShow)
        }
    }

}