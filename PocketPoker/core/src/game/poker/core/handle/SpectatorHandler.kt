package game.poker.core.handle

import com.google.gson.JsonObject
import game.poker.Settings
import game.poker.core.WebSocketConnection
import game.poker.screens.TableScreen
import java.util.*

class SpectatorHandler(conn: WebSocketConnection,
                       queue: Queue<String>,
                       table: TableScreen) : Handler(conn, table, queue) {

    override fun open() {
        val json = JsonObject()
        json.addProperty("type", "sp")
        json.addProperty("name", Settings.currTableId.toString())
        json.addProperty("id", Settings.currTournamentId)
        json.addProperty("password", "")
        socket.connectToServer(json.toString())
    }

    override fun initHand(data: JsonObject) {
        if(waitForInit){
            val tableNum = data["table_number"].asLong.insertSpaces()
            val isFinal = data["is_final"].asBoolean
            info.watchingTable(tableNum, isFinal)
            val json = JsonObject()
            json.addProperty("type", "nick")
            json.addProperty("nick", Settings.nick)
            socket.send(json.toString())
        }
        table.currView.setPlayersLeft(data["players_left"].asLong.insertSpaces())
        super.initHand(data)
    }

}