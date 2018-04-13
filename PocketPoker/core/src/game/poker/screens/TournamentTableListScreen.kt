package game.poker.screens

import com.google.gson.JsonObject
import game.poker.PocketPoker
import game.poker.Settings
import game.poker.staticFiles.Texts
import game.poker.staticFiles.Texts.TextKeys
import game.poker.gui.menu.TournamentTableItem

class TournamentTableListScreen(game: PocketPoker): BaseTableListScreen(game) {

    init {
        backButton.setText(Texts[TextKeys.TOURNAMENT])
        backButton.addListener(game.switches[ScreenType.TOURNAMENT])
    }

    override fun updateList() {

        if (tablesData.size() == 1) handleItemClick(tablesData[0].asJsonObject["id"].asInt)
        tableList.clear()
        for (field in tablesData) {
            val item = field.asJsonObject
            val id = item["id"].asInt
            var name = Texts[TextKeys.TABLE] + " #" + id.toString()
            if (id == 0) name = Texts[TextKeys.FINAL_TABLE]
            if (name.contains(searchEdit.text, true)) tableList.add(TournamentTableItem(id, name))
        }

    }

    override fun sendRequestForUpdateList() {
        val data = JsonObject()
        data.addProperty("type", "get tournament tables")
        data.addProperty("id", Settings.currTournamentId)
        game.menuHandler.sendToServer(data)
    }

    override fun receiveFromServer(json: JsonObject) {
        if (json["type"].asString == "get tournament tables") {
            tablesData = json["info"].asJsonArray
            needUpdate = true
        }
    }

    override fun update() {

    }

    override fun handleItemClick(id: Int) {
        Settings.currTableId = id
        Settings.currTableMode = Settings.TableMode.Spectate
        game.setCurrScreen(ScreenType.TABLE)
    }

}