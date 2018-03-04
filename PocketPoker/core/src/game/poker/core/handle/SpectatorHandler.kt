package game.poker.core.handle

import com.google.gson.JsonObject
import game.poker.core.WebSocketConnection

class SpectatorHandler(val tableToSpactate: String,
                       val nick: String
                       conn: WebSocketConnection,
                       table: TableScreen) : Handler(conn, table) {

    override fun open() {
        TODO("not implemented")
    }

    override fun initHand(data: JsonObject) {
        if(waitForInit){
            info.watchingTable()
        }
        table.setPlayersLeft(data["players_left"].asLong.insertSpaces())
        super.initHand(data)
    }

}