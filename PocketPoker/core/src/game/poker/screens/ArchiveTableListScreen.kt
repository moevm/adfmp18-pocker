package game.poker.screens

import com.google.gson.JsonObject
import game.poker.PocketPoker
import game.poker.Settings
import kotlin.concurrent.withLock
import game.poker.gui.TableItem

class ArchiveTableListScreen(game: PocketPoker): TableListScreen(game) {

    override fun updateList() {
        val data = JsonObject()
        data.addProperty("type", "get replay tables")
        data.addProperty("id", Settings.currArchiveTournamentId)
        game.menuHandler.sendToServer(data)
    }

    override fun receiveFromServer(json: JsonObject) {

        if (json["type"].asString == "replay tables") {

            val info = json["info"].asJsonArray
            if (info.size() == 1) handleItemClick(info[0].asJsonObject["id"].asInt)

            lock.withLock {
                tableList.clear()
                for (field in info) {
                    val item = field.asJsonObject
                    val id = item["id"].asInt
                    val hands = item["hands"].asInt
                    var name = Settings.getText(Settings.TextKeys.TABLE) + " #" + id.toString()
                    if (id == 0) name = Settings.getText(Settings.TextKeys.FINAL_TABLE)
                    tableList.add(TableItem(id, name, hands))
                }
            }
        }
    }

    override fun handleItemClick(id: Int) {
        Settings.currTableId = id
        game.setCurrScreen(ScreenType.TABLE)
    }

}