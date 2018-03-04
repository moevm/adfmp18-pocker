package game.poker.core.handle

import java.util.*
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import game.poker.Settings
import game.poker.core.Card
import game.poker.core.InfoCreator
import game.poker.core.Seats
import game.poker.core.WebSocketConnection
import game.poker.screens.TableScreen

fun Long.insertSpaces(): String{
    val num = this.toString()
    //val space = '\u2009'
    val space = ' '

    return when(num.length){
        in 0..3 -> num

        in 4..6 -> num.substring(0..num.length - 3) + space +
                   num.substring(num.length - 3..num.length)

        in 7..9 -> num.substring(0..num.length - 6) + space +
                   num.substring(num.length - 6..num.length - 3) + space +
                   num.substring(num.length - 3..num.length)

        else -> num.substring(0..num.length - 9) + space +
                num.substring(num.length - 9..num.length - 6) + space +
                num.substring(num.length - 6..num.length - 3) + space +
                num.substring(num.length - 3..num.length)
    }
}

fun Long.shortcut(): String{
    return when(this){
        in 0 until 1_000 -> this.toString()

        in 1_000 until 10_000 -> {
            val num = this.toString()
            num.substring(0..num.length-3) + ' ' +
                    num.substring(num.length-3..num.length)
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


abstract class Handler(val socket: WebSocketConnection,
                       val table: TableScreen) {
    val queue: Queue<String> = LinkedList<String>()

    var inLoop = false
    var reconnectMode = false
    var waitForInit = true
    var gameMode = false
    lateinit var seats: Seats
    val info = InfoCreator(table)

    val parser = JsonParser()

    val systemHandle = mapOf<String, (JsonObject) -> Unit>(
        "broken" to  ::broken,
            "finish" to ::finish,
            "info" to ::infoMessage,
            "reconnect start" to ::reconnectStart,
            "reconnect end" to ::reconnectEnd
    )

    val gameHandle = mapOf<String, (JsonObject) -> Unit>(
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

            println("DATA: ${data.asString}")

            gameHandle[type]?.invoke(data)
        }
    }

    open protected fun canMoveChips() = !reconnectMode

    open protected fun chatMessage(message: String){
        val json = JsonObject()
        json.addProperty("type", "chat")
        json.addProperty("text", message)
        socket.send(json.toString())
    }

    open protected fun broken(data: JsonObject){
        socket.close()
        inLoop = false
    }

    open protected fun finish(data: JsonObject){
        info.finish()
        inLoop = false
    }

    open protected fun infoMessage(data: JsonObject){
        info.basic()
    }

    open protected fun reconnectStart(data: JsonObject){
        reconnectMode = true
    }

    open protected fun reconnectEnd(data: JsonObject){
        reconnectMode = false
    }

    open protected fun initHand(data: JsonObject){
        if(waitForInit){
            waitForInit = false
            table.initTable()
        }

        val tableNumber = data["table_number"].asLong.insertSpaces()

        if(data["is_final"].asBoolean){
            table.setTableNum(Settings.getText(Settings.TextKeys.FINAL_TABLE))
        }
        else{
            table.setTableNum("${Settings.getText(Settings.TextKeys.TABLE)} #$tableNumber")
        }

        val handNumber = data["hand_number"].asLong.insertSpaces()
        val ante = data["ante"].asLong.shortcut()
        val sb = data["sb"].asLong.shortcut()
        val bb = data["bb"].asLong.shortcut()
        val avgStack = data["avg_stack"].asLong.insertSpaces()
        val playersLeft = data["players_left"].asLong.insertSpaces()

        table.setHandNum(handNumber)
        table.setBlinds(sb, bb, ante)
        table.setAverageStack(avgStack)
        table.setPlayersLeft(playersLeft)

        seats = Seats(table, data, gameMode)

        val top9 = mutableListOf<Pair<String, String>>()

        for(player in data["top_9"].asJsonArray){
            val json = player.asJsonObject
            val name = json["name"].asString
            val stack = json["stack"].asLong.insertSpaces()
            top9.add(Pair(name, stack))
        }

        table.setTopPlayers(top9)
    }

    open protected fun ante(data: JsonObject){
        val paid = data["paid"].asJsonArray
        for(player in paid){
            val json = player.asJsonObject
            seats.setBet(json["id"].asInt, json["paid"].asLong,
                    Settings.getText(Settings.TextKeys.ANTE))
        }
    }

    open protected fun collectMoney(data: JsonObject){
        TODO()
    }

    open protected fun blinds(data: JsonObject){
        val buttonId = data["button"].asInt
        table.setDealerPos(seats.getById(buttonId).localSeat)
        val info = data["info"].asJsonArray
        if(info.size() == 1){
            seats.setBet(info[0].asJsonObject["id"].asInt,
                    info[0].asJsonObject["paid"].asLong,
                    Settings.getText(Settings.TextKeys.BIG_BLIND))
        }
        else{
            seats.setBet(info[0].asJsonObject["id"].asInt,
                    info[0].asJsonObject["paid"].asLong,
                    Settings.getText(Settings.TextKeys.SMALL_BLIND))
            seats.setBet(info[1].asJsonObject["id"].asInt,
                    info[1].asJsonObject["paid"].asLong,
                    Settings.getText(Settings.TextKeys.BIG_BLIND))
        }
    }

    open protected fun blindsIncreased(data: JsonObject){
        val bb = data["bb"].asLong.shortcut()
        val sb = data["sb"].asLong.shortcut()
        val ante = data["ante"].asLong.shortcut()
        info.blindsIncreasing()
        table.setBlinds(sb, bb, ante)
    }

    open protected fun giveCards(data: JsonObject){
        println("Handler.give_cards()")
    }

    open protected fun dealCards(data: JsonObject){
        table.dealCards()
    }

    open protected fun deletePlayer(data: JsonObject){
        seats.deletePlayer(data["id"].asInt)
    }

    open protected fun addPlayer(data: JsonObject){
        seats.addPlayer(data)
    }

    open protected fun resit(data: JsonObject){
        println("Handler.resit()")
    }

    open protected fun switchDecision(data: JsonObject){
        seats.idInDecision = data["id"].asInt
        table.switchDecision(seats.idToLocalSeat[data["id"].asInt]!!)
    }

    open protected fun madeDecision(data: JsonObject){
        val seat = seats.getById(seats.idInDecision)
        when(data["result"].asString){
            "fold" -> {
                table.hideCards(seat.localSeat)
                seats.updateInfo(seat.id, Settings.getText(Settings.TextKeys.FOLD))
            }
            "check" -> {
                seats.updateInfo(seat.id, Settings.getText(Settings.TextKeys.CHECK))
            }
            "call" -> {
                seats.setBet(seat.id, data["money"].asLong,
                        Settings.getText(Settings.TextKeys.CALL))
            }
            "raise" -> {
                seats.setBet(seat.id, data["money"].asLong,
                        Settings.getText(Settings.TextKeys.RAISE))
            }
            "all in" -> {
                seats.setBet(seat.id, data["money"].asLong,
                        Settings.getText(Settings.TextKeys.ALL_IN))
            }
            else -> {
                throw IllegalArgumentException("bad result")
            }
        }
    }

    open protected fun excessMoney(data: JsonObject){
        val id = data["id"].asInt
        val money = data["money"].asLong
        seats.setBet(id, seats.getById(id).gived - money)
    }

    open protected fun flop(data: JsonObject){
        seats.clearDecisionStates()
        seats.clearDecisions()
        val flop1 = Card.fromString(data["card1"].asString)
        val flop2 = Card.fromString(data["card2"].asString)
        val flop3 = Card.fromString(data["card3"].asString)
        table.setFlopCards(flop1, flop2, flop3)
    }

    open protected fun turn(data: JsonObject){
        seats.clearDecisionStates()
        seats.clearDecisions()
        table.setTurnCard(Card.fromString(data["card"].asString))
    }

    open protected fun river(data: JsonObject){
        seats.clearDecisionStates()
        seats.clearDecisions()
        table.setRiverCard(Card.fromString(data["card"].asString))
    }

    open protected fun openCards(data: JsonObject){
        seats.clearDecisionStates()
        seats.clearDecisions()
        val players = data["cards"].asJsonArray
        for(player in players){
            val json = player.asJsonObject
            val seat = seats.getById(json["id"].asInt)
            val card1 = Card.fromString(json["card1"].asString)
            val card2 = Card.fromString(json["card2"].asString)
            table.setPlayerCards(seat.localSeat, card1, card2)
        }
    }

    open protected fun giveMoney(data: JsonObject){
        TODO()
    }

    open protected fun moneyResults(data: JsonObject){
        println("Handler.money_results()")
    }

    open protected fun handResults(data: JsonObject){
        TODO()
    }

    open protected fun busted(data: JsonObject){
        println("Handler.busted()")
    }

    open protected fun clear(data: JsonObject){
        seats.clear()
    }

    open protected fun win(data: JsonObject){
        println("Handler.win()")
    }

    open protected fun place(data: JsonObject){
        table.setCurrPlace(data["place"].asLong.insertSpaces())
    }

    open protected fun chat(data: JsonObject){
        table.addToChat(data["text"].asString)
    }

    open protected fun disconnected(data: JsonObject){
        val seat = seats.getById(data["id"].asInt)
        seat.disconnected = true
        table.setPlayerDisconnected(seat.localSeat, true)
    }

    open protected fun connected(data: JsonObject){
        val seat = seats.getById(data["id"].asInt)
        seat.disconnected = false
        table.setPlayerDisconnected(seat.localSeat, false)
    }

    open protected fun kick(data: JsonObject){
        println("Handler.kick()")
    }

    open protected fun backCounting(data: JsonObject){
        seats.updateInfo(data["id"].asInt,
                data["time"].asString + " " + Settings.getText(Settings.TextKeys.SECONDS))
    }

    open protected fun setDecision(data: JsonObject){
        println("Handler.set_decision()")
    }

}