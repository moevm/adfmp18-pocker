package game.poker.screens

import com.badlogic.gdx.Screen
import com.google.gson.JsonObject

enum class ScreenType{
    MAIN_MENU,
    TOURNAMENT,
    ARCHIVE,
    SETTINGS,
    TABLE
}

interface BaseScreen : Screen {
    fun update()
    fun receiveFromServer(json: JsonObject)
}