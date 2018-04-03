package game.poker.screens

import com.badlogic.gdx.Screen
import com.google.gson.JsonObject

enum class ScreenType{
    MAIN_MENU,
    TOURNAMENT,
    ARCHIVE,
    SETTINGS,
    TABLE,
    ARCHIVE_TABLE_LIST,
    CREATE_TOURNAMENT,
    TOURNAMENT_TABLE_LIST
}

interface BaseScreen : Screen {
    fun update()
    fun receiveFromServer(json: JsonObject)
}