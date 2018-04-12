package game.poker.core.handle

import com.google.gson.JsonObject
import game.poker.core.WebSocketConnection
import game.poker.screens.TableScreen
import java.util.*


class ReplayHandler(val replayId: String,
                    conn: WebSocketConnection,
                    queue: Queue<String>,
                    table: TableScreen) : Handler(conn, table, queue) {

    var inPause = false

    override fun open() {
        val json = JsonObject()
        json.addProperty("type", "rp")
        json.addProperty("name", replayId)
        socket.connectToServer(json.toString())
    }

    override fun chatMessage(message: String) {
        // Need to rewrite chat_message method for replay to do nothing
        println("Replay.chat_message()")
    }

    override fun canMoveChips() = !inPause

    fun pausePlay(){
        if(inPause){
            inPause = false
            socket.send("play")
        }
        else{
            inPause = true
            socket.send("pause")
            seats.movingBets = false
        }
    }

    fun nextStep(){
        socket.send("next step")
    }

    fun prevHand(){
        socket.send("prev hand")
        seats.movingBets = false
    }

    fun nextHand(){
        socket.send("next hand")
        seats.movingBets = false
    }

    override fun initHand(data: JsonObject) {
        if(waitForInit){
            info.watchingTable()
        }
        table.currView.setPlayersLeft(data["players_left"].asLong.insertSpaces())
        super.initHand(data)
    }

}