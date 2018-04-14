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

        if (tablesData.size() == 1) {
            Settings.isOnlyOneTable = true
            handleItemClick(tablesData[0].asJsonObject["id"].asInt)
        }
        else{
            Settings.isOnlyOneTable = false
            tableList.clear()
            for (field in tablesData) {
                val item = field.asJsonObject
                val id = item["id"].asInt
                val name = if(id != 0) "${Texts[TextKeys.TABLE]} #$id" else Texts[TextKeys.FINAL_TABLE]
                if (name.contains(searchEdit.text, true)) tableList.add(TournamentTableItem(id, name))
            }
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

    override fun setPreviousScreen() {
        game.setCurrScreen(ScreenType.TOURNAMENT)
    }

}