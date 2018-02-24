package game.poker.screens

import com.badlogic.gdx.Screen

enum class ScreenType{
    MAIN_MENU,
    SETTINGS
}

interface BaseScreen : Screen {
    fun update()
}