package game.poker.screens

import com.google.gson.JsonObject
import game.poker.PocketPoker
import game.poker.Settings
import game.poker.gui.menu.*
import game.poker.staticFiles.Texts
import game.poker.staticFiles.Texts.TextKeys

class ArchiveTableListScreen(game: PocketPoker): BaseTableListScreen(game) {

    init {
        backButton.setText(Texts[TextKeys.ARCHIVE])
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
            var name = Texts[TextKeys.TABLE] + " #" + id.toString()
            if (id == 0) name = Texts[TextKeys.FINAL_TABLE]
            if (name.contains(searchEdit.text, true)) tableList.add(ArchiveTableItem(id, name, hands))
        }

    }

    override fun receiveFromServer(json: JsonObject) {
        if (json["type"].asString == "replay tables") {
            tablesData = json["info"].asJsonArray
            needUpdate = true
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