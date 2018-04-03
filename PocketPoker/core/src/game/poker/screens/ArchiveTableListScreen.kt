package game.poker.screens

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import game.poker.PocketPoker
import game.poker.Settings
import game.poker.gui.ArchiveTableItem

class ArchiveTableListScreen(game: PocketPoker): BaseTableListScreen(game) {

    init {
        backButton.setText(Settings.getText(Settings.TextKeys.ARCHIVE))
        backButton.addListener(game.switches[ScreenType.ARCHIVE])
    }

    override fun sendRequestForUpdateList() {
        val data = JsonObject()
        data.addProperty("type", "get replay tables")
        data.addProperty("id", Settings.currArchiveTournamentId)
        game.menuHandler.sendToServer(data)
    }

    override fun updateList() {

        if (tablesData.size() == 1) handleItemClick(tablesData[0].asJsonObject["id"].asInt)
        tableList.clear()
        for (field in tablesData) {
            val item = field.asJsonObject
            val id = item["id"].asInt
            val hands = item["hands"].asInt
            var name = Settings.getText(Settings.TextKeys.TABLE) + " #" + id.toString()
            if (id == 0) name = Settings.getText(Settings.TextKeys.FINAL_TABLE)
            tableList.add(ArchiveTableItem(id, name, hands))
        }
        tablesData = JsonArray()

    }

    override fun receiveFromServer(json: JsonObject) {
        if (json["type"].asString == "replay tables") {
            tablesData = json["info"].asJsonArray
        }
    }

    override fun update() {

    }

    override fun handleItemClick(id: Int) {
        Settings.currTableId = id
        Settings.currTableMode = Settings.TableMode.Replay
        game.setCurrScreen(ScreenType.TABLE)
    }

}