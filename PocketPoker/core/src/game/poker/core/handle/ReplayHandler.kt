package game.poker.core.handle

import com.google.gson.JsonObject
import game.poker.Settings
import game.poker.core.WebSocketConnection
import game.poker.screens.TableScreen
import java.util.*


class ReplayHandler(conn: WebSocketConnection,
                    queue: Queue<String>,
                    table: TableScreen) : Handler(conn, table, queue) {

    var inPause = false

    override fun open() {
        val replayId = Settings.currArchiveTournamentId.toString() + ":" + Settings.currTableId
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
        if(waitForInit) {
            val tableNum = data["table_number"].asLong.insertSpaces()
            val isFinal = data.has("is_final") && data["is_final"].asBoolean || tableNum == "0"
            info.watchingTable(tableNum, isFinal)
        }
        table.currView.setPlayersLeft(data["players_left"].asLong.insertSpaces())
        super.initHand(data)
    }

}