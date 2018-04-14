package game.poker.core.handle

import java.util.*
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import game.poker.core.*
import game.poker.screens.TableScreen
import game.poker.staticFiles.Sounds
import game.poker.staticFiles.Texts
import game.poker.staticFiles.Texts.TextKeys

fun Long.insertSpaces(): String{
    val num = this.toString()
    //val space = '\u2009'
    val space = ' '

    return when(num.length){
        in 0..3 -> num

        in 4..6 -> num.substring(0 until num.length - 3) + space +
                   num.substring(num.length - 3 until num.length)

        in 7..9 -> num.substring(0 until num.length - 6) + space +
                   num.substring(num.length - 6 until num.length - 3) + space +
                   num.substring(num.length - 3 until num.length)

        else -> num.substring(0 until num.length - 9) + space +
                num.substring(num.length - 9 until num.length - 6) + space +
                num.substring(num.length - 6 until num.length - 3) + space +
                num.substring(num.length - 3 until num.length)
    }
}

fun Long.shortcut(): String{
    return when(this){
        in 0 until 1_000 -> this.toString()

        in 1_000 until 10_000 -> {
            val num = this.toString()
            num.substring(0 until num.length-3) + ' ' +
                    num.substring(num.length-3 until num.length)
        }

        in 10_000 until 100_000 -> {
            // 46776 -> 46.7k
            (Math.floor(this / 100.0) / 10).toString() + 'k'
        }

        in 100_000 until 1_000_000 -> {
            // 123231 -> 123k
            Math.floor(this / 1000.0).toString() + 'k'
        }

        in 1_000_000 until 10_000_000 -> {
            // 3 123 345 -> 3.12m
            (Math.floor(this / 10000.0)/100).toString() + 'm'
        }

        in 10_000_000 until 100_000_000 -> {
            // 12 345 678 -> 12.3m
            ((Math.floor(this / 100000.0))/10).toString() + 'm'
        }

        in 100_000_000 until 1_000_000_000 -> {
            // 123 345 678 -> 123m
            Math.floor(this / 1000000.0).toString() + 'm'
        }

        in 1_000_000_000 until 10_000_000_000 -> {
            // 1 123 345 678 -> 1.12b
            (Math.floor(this / 10000000.0)/100) .toString() + 'b'
        }

        in 10_000_000_000 until 100_000_000_000 -> {
            // 12 123 345 678 -> 12.1b
            (Math.floor(this / 100000000.0)/10).toString() + 'b'
        }

        else -> {
            // 122 223 345 678 -> 123b
            Math.floor(this / 1000000000.0).toString() + 'b'
        }
    }
}


abstract class Handler(protected val socket: WebSocketConnection,
                       protected val table: TableScreen,
                       private val queue: Queue<String>) {

    var inLoop = false
    var reconnectMode = false
    var waitForInit = true
    var gameMode = false
    lateinit var seats: Seats
    val info = InfoCreator(table)

    private val parser = JsonParser()

    private val systemHandle = mapOf<String, (JsonObject) -> Unit>(
            "broken" to  ::broken,
            "finish" to ::finish,
            "info" to ::infoMessage,
            "reconnect start" to ::reconnectStart,
            "reconnect end" to ::reconnectEnd
    )

    private val gameHandle = mapOf<String, (JsonObject) -> Unit>(
            "init hand" to ::initHand,
            "ante" to ::ante,
            "collect money" to ::collectMoney,
            "blinds" to ::blinds,
            "blinds increased" to ::blindsIncreased,
            "give cards" to ::giveCards,
            "deal cards" to ::dealCards,
            "delete player" to ::deletePlayer,
            "add player" to ::addPlayer,
            "resit" to ::resit,
            "switch decision" to ::switchDecision,
            "made decision" to ::madeDecision,
            "excess money" to ::excessMoney,
            "flop" to ::flop,
            "turn" to ::turn,
            "river" to ::river,
            "open cards" to ::openCards,
            "give money" to ::giveMoney,
            "money results" to ::moneyResults,
            "hand results" to ::handResults,
            "busted" to ::busted,
            "clear" to ::clear,
            "win" to ::win,
            "place" to ::place,
            "chat" to ::chat,
            "disconnected" to ::disconnected,
            "connected" to ::connected,
            "kick" to ::kick,
            "back counting" to ::backCounting,
            "set decision" to ::setDecision
    )

    abstract fun open()

    fun close(){
        inLoop = false
        socket.close()
    }

    fun handle(){
        inLoop = true

        while(inLoop){

            if(!reconnectMode){
                Thread.sleep(10)
            }

            if(queue.isEmpty()){
                continue
            }

            val data =  parser.parse(queue.remove()).asJsonObject
            val type = data["type"].asString

            systemHandle[type]?.invoke(data)

            if(!reconnectMode && waitForInit && type != "init hand"){
                continue
            }

            gameHandle[type]?.invoke(data)
        }
    }

    fun playSound(sound: Sounds.SoundType){
        if(!reconnectMode){
            Sounds.play(sound)
        }
    }

    protected open fun canMoveChips() = !reconnectMode

    protected open fun chatMessage(message: String){
        val json = JsonObject()
        json.addProperty("type", "chat")
        json.addProperty("text", message)
        socket.send(json.toString())
    }

    protected open fun broken(data: JsonObject){
        socket.close()
        inLoop = false
    }

    protected open fun finish(data: JsonObject){
        info.finish()
        inLoop = false
    }

    protected open fun infoMessage(data: JsonObject){
        info.basic()
    }

    protected open fun reconnectStart(data: JsonObject){
        reconnectMode = true
    }

    protected open fun reconnectEnd(data: JsonObject){
        reconnectMode = false
    }

    protected open fun initHand(data: JsonObject){
        if(waitForInit){
            waitForInit = false
            table.currView.initTable()
        }

        val tableNumber = data["table_number"].asLong.insertSpaces()

        if(data.has("is_final") && data["is_final"].asBoolean ||
                data["table_number"].asInt == 0){
            table.currView.setTableNum(Texts[TextKeys.FINAL_TABLE])
        }
        else{
            table.currView.setTableNum("${Texts[TextKeys.TABLE]} #$tableNumber")
        }

        val handNumber = data["hand_number"].asLong.insertSpaces()
        val ante = data["ante"].asLong.shortcut()
        val sb = data["sb"].asLong.shortcut()
        val bb = data["bb"].asLong.shortcut()
        val avgStack = data["avg_stack"].asLong.insertSpaces()
        val playersLeft = data["players_left"].asLong.insertSpaces()

        table.currView.setHandNum(handNumber)
        table.currView.setBlinds(sb, bb, ante)
        table.currView.setAverageStack(avgStack)
        table.currView.setPlayersLeft(playersLeft)

        seats = Seats(table, data, gameMode)

        val top9 = mutableListOf<Pair<String, String>>()

        for(player in data["top_9"].asJsonArray){
            val json = player.asJsonObject
            val name = json["name"].asString
            val stack = json["stack"].asLong.insertSpaces()
            top9.add(Pair(name, stack))
        }

        table.currView.setTopPlayers(top9)
    }

    protected open fun ante(data: JsonObject){
        val paid = data["paid"].asJsonArray
        for(player in paid){
            val json = player.asJsonObject
            seats.setBet(json["id"].asInt, json["paid"].asLong,
                    Texts[TextKeys.ANTE])
        }
        playSound(Sounds.SoundType.CHIPS)
    }

    protected open fun collectMoney(data: JsonObject){
        var needMove = false
        for(seat in seats.all()){
            if(seat.gived > 0){
                needMove = true
                seats.mainChips += seat.gived
                seat.stack -= seat.gived
                if(!canMoveChips()){
                    seats.setBet(seat.id, 0)
                }
            }
        }
        if(needMove){
            if(!canMoveChips()){
                seats.setBet(-1, seats.mainChips)
            }
            else{
                table.currView.moveChipsToPot()
            }
        }
        seats.movingBets = true
        for(seat in seats.all()){
            if(seat.gived > 0){
                seats.setBet(seat.id, 0)
            }
        }
        seats.setBet(-1, seats.mainChips)
        seats.clearDecisionStates()
        playSound(Sounds.SoundType.COLLECT)
    }

    protected open fun blinds(data: JsonObject){
        seats.movingBets = false
        val buttonId = data["button"].asInt
        table.currView.setDealerPos(seats.getById(buttonId).localSeat)
        val info = data["info"].asJsonArray
        if(info.size() == 1){
            seats.setBet(info[0].asJsonObject["id"].asInt,
                    info[0].asJsonObject["paid"].asLong,
                    Texts[TextKeys.BIG_BLIND])
        }
        else{
            seats.setBet(info[0].asJsonObject["id"].asInt,
                    info[0].asJsonObject["paid"].asLong,
                    Texts[TextKeys.SMALL_BLIND])
            seats.setBet(info[1].asJsonObject["id"].asInt,
                    info[1].asJsonObject["paid"].asLong,
                    Texts[TextKeys.BIG_BLIND])
        }
        playSound(Sounds.SoundType.CHIPS)
    }

    protected open fun blindsIncreased(data: JsonObject){
        val bb = data["bb"].asLong.shortcut()
        val sb = data["sb"].asLong.shortcut()
        val ante = data["ante"].asLong.shortcut()
        info.blindsIncreasing()
        table.currView.setBlinds(sb, bb, ante)
    }

    protected open fun giveCards(data: JsonObject){
        println("Handler.give_cards()")
    }

    protected open fun dealCards(data: JsonObject){
        table.currView.dealCards()
    }

    protected open fun deletePlayer(data: JsonObject){
        seats.deletePlayer(data["id"].asInt)
    }

    protected open fun addPlayer(data: JsonObject){
        seats.addPlayer(data)
    }

    protected open fun resit(data: JsonObject){
        println("Handler.resit()")
    }

    protected open fun switchDecision(data: JsonObject){
        seats.idInDecision = data["id"].asInt
        table.currView.switchDecision(seats.idToLocalSeat[data["id"].asInt]!!)
    }

    protected open fun madeDecision(data: JsonObject){
        val seat = seats.getById(seats.idInDecision)
        when(data["result"].asString){
            "fold" -> {
                table.currView.hideCards(seat.localSeat)
                seats.updateInfo(seat.id, Texts[TextKeys.FOLD])
                playSound(Sounds.SoundType.FOLD)
            }
            "check" -> {
                seats.updateInfo(seat.id, Texts[TextKeys.CHECK])
                playSound(Sounds.SoundType.CHECK)
            }
            "call" -> {
                seats.setBet(seat.id, data["money"].asLong,
                        Texts[TextKeys.CALL])
                playSound(Sounds.SoundType.CHIPS)
            }
            "raise" -> {
                seats.setBet(seat.id, data["money"].asLong,
                        Texts[TextKeys.RAISE])
                playSound(Sounds.SoundType.CHIPS)
            }
            "all in" -> {
                seats.setBet(seat.id, data["money"].asLong,
                        Texts[TextKeys.ALL_IN])
                playSound(Sounds.SoundType.CHIPS)
            }
            else -> {
                throw IllegalArgumentException("bad result")
            }
        }
    }

    protected open fun excessMoney(data: JsonObject){
        val id = data["id"].asInt
        val money = data["money"].asLong
        seats.setBet(id, seats.getById(id).gived - money)
    }

    protected open fun flop(data: JsonObject){
        seats.clearDecisionStates()
        seats.clearDecisions()
        val flop1 = Card.fromString(data["card1"].asString)
        val flop2 = Card.fromString(data["card2"].asString)
        val flop3 = Card.fromString(data["card3"].asString)
        table.currView.setFlopCards(flop1, flop2, flop3)
        playSound(Sounds.SoundType.FOLD)
    }

    protected open fun turn(data: JsonObject){
        seats.clearDecisionStates()
        seats.clearDecisions()
        table.currView.setTurnCard(Card.fromString(data["card"].asString))
        playSound(Sounds.SoundType.FOLD)
    }

    protected open fun river(data: JsonObject){
        seats.clearDecisionStates()
        seats.clearDecisions()
        table.currView.setRiverCard(Card.fromString(data["card"].asString))
        playSound(Sounds.SoundType.FOLD)
    }

    protected open fun openCards(data: JsonObject){
        seats.clearDecisionStates()
        seats.clearDecisions()
        val players = data["cards"].asJsonArray
        for(player in players){
            val json = player.asJsonObject
            val seat = seats.getById(json["id"].asInt)
            if(json["card1"].asString != "UP" && json["card2"].asString != "UP"){
                val card1 = Card.fromString(json["card1"].asString)
                val card2 = Card.fromString(json["card2"].asString)
                table.currView.setPlayerCards(seat.localSeat, card1, card2)
            }
        }
    }

    protected open fun giveMoney(data: JsonObject){
        seats.clearDecisionStates()
        val id = data["id"].asInt
        val money = data["money"].asLong

        val seat = seats.getById(id)

        seat.gived += money
        seats.setBet(id, money, "Win")

        seats.mainChips -= money
        seats.setBet(-1, seats.mainChips, "Win")

        if(canMoveChips()){
            table.currView.moveChipsFromPot(seat.localSeat, money)
        }
        playSound(Sounds.SoundType.GRAB)
    }

    protected open fun moneyResults(data: JsonObject){
        println("Handler.money_results()")
    }

    protected open fun handResults(data: JsonObject){
        // TODO
    }

    protected open fun busted(data: JsonObject){
        println("Handler.busted()")
    }

    protected open fun clear(data: JsonObject){
        seats.clear()
    }

    protected open fun win(data: JsonObject){
        println("Handler.win()")
    }

    protected open fun place(data: JsonObject){
        table.currView.setCurrPlace(data["place"].asLong.insertSpaces())
    }

    protected open fun chat(data: JsonObject){
        table.currView.addToChat(data["text"].asString)
    }

    protected open fun disconnected(data: JsonObject){
        val seat = seats.getById(data["id"].asInt)
        seat.disconnected = true
        table.currView.setPlayerDisconnected(seat.localSeat, true)
    }

    protected open fun connected(data: JsonObject){
        val seat = seats.getById(data["id"].asInt)
        seat.disconnected = false
        table.currView.setPlayerDisconnected(seat.localSeat, false)
    }

    protected open fun kick(data: JsonObject){
        println("Handler.kick()")
    }

    protected open fun backCounting(data: JsonObject){
        seats.updateInfo(data["id"].asInt,
                data["time"].asString + " " + Texts[TextKeys.SECONDS])
    }

    protected open fun setDecision(data: JsonObject){
        println("Handler.set_decision()")
    }

}