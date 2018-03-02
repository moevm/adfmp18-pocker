package game.poker.core

import java.util.*
import com.google.gson.JsonObject

class Handler(val socket: WebSocketConnection) {
    val queue: Queue<String> = LinkedList<String>()

    var inLoop = false
    var reconnectMode = false
    var waitForInit = true
    var gameMode = false
    var seats: Seats = null
    val info = InfoCreator()

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

    fun handle(){

    }

    fun canMoveChips() = !reconnectMode

    fun chatMessage(message: String){
        val json = JsonObject()
        json.addProperty("type", "chat")
        json.addProperty("text", message)
        socket.send(json.asString)
    }

    fun broken(_: JsonObject){
        socket.close()
        inLoop = false
    }

    fun finish(data: JsonObject){
        info.finish()
        inLoop = false
    }

    fun infoMessage(data: JsonObject){
        info.basic()
    }

    fun reconnectStart(_: JsonObject){
        reconnectMode = true
    }

    fun reconnectEnd(_: JsonObject){
        reconnectMode = false
    }

    fun initHand(data: JsonObject){

    }

    fun ante(data: JsonObject){

    }

    fun collectMoney(data: JsonObject){

    }

    fun blinds(data: JsonObject){

    }

    fun blindsIncreased(data: JsonObject){

    }

    fun giveCards(data: JsonObject){

    }

    fun dealCards(data: JsonObject){

    }

    fun deletePlayer(data: JsonObject){

    }

    fun addPlayer(data: JsonObject){

    }

    fun resit(data: JsonObject){

    }

    fun switchDecision(data: JsonObject){

    }

    fun madeDecision(data: JsonObject){

    }

    fun excessMoney(data: JsonObject){

    }

    fun flop(data: JsonObject){

    }

    fun turn(data: JsonObject){

    }

    fun river(data: JsonObject){

    }

    fun openCards(data: JsonObject){

    }

    fun giveMoney(data: JsonObject){

    }

    fun moneyResults(data: JsonObject){

    }

    fun handResults(data: JsonObject){

    }

    fun busted(data: JsonObject){

    }

    fun clear(data: JsonObject){
        seats.clear()
    }

    fun win(data: JsonObject){

    }

    fun place(data: JsonObject){

    }

    fun chat(data: JsonObject){

    }

    fun disconnected(data: JsonObject){

    }

    fun connected(data: JsonObject){

    }

    fun kick(data: JsonObject){

    }

    fun backCounting(data: JsonObject){

    }

    fun setDecision(data: JsonObject){

    }

}