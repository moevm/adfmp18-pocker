package game.poker.core.handle

import com.google.gson.JsonObject
import game.poker.core.WebSocketConnection
import game.poker.screens.TableScreen
import java.util.*

class SpectatorHandler(val tableToSpactate: String,
                       val nick: String,
                       conn: WebSocketConnection,
                       queue: Queue<String>,
                       table: TableScreen) : Handler(conn, table, queue) {

    override fun open() {
        TODO("not implemented")
    }

    override fun initHand(data: JsonObject) {
        if(waitForInit){
            info.watchingTable()
        }
        table.currView.setPlayersLeft(data["players_left"].asLong.insertSpaces())
        super.initHand(data)
    }

}