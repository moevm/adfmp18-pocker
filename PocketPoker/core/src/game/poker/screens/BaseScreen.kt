package game.poker.screens

import com.badlogic.gdx.Screen

enum class ScreenType{
    MAIN_MENU,
    TOURNAMENT,
    SETTINGS,
    ARCHIVE
}

interface BaseScreen : Screen {
    fun update()
}