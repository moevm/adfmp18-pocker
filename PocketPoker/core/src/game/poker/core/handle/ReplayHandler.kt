package game.poker.core.handle

import com.google.gson.JsonObject
import game.poker.core.WebSocketConnection
import game.poker.screens.TableScreen


class ReplayHandler(val replayId: String,
                    conn: WebSocketConnection,
                    table: TableScreen) : Handler(conn, table) {

    var inPause = false

    override fun open() {
        TODO("not implemented")
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
        }
    }

    fun nextStep(){
        socket.send("next step")
    }

    fun prevHand(){
        TODO()
    }

    fun nextHand(){
        TODO()
    }

    override fun initHand(data: JsonObject) {
        if(waitForInit){
            info.watchingTable()
        }
        table.currView.setPlayersLeft(data["players_left"].asLong.insertSpaces())
        super.initHand(data)
    }

}