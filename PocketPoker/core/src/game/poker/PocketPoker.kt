package game.poker

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.badlogic.gdx.utils.viewport.Viewport

import game.poker.screens.MainMenu
import game.poker.screens.SettingsMenu

class PocketPoker : Game() {

    val gameWidth = 1080f
    val gameHeight = 1920f

    lateinit var view: Viewport private set
    lateinit var settingsMenu: SettingsMenu private set
    lateinit var mainMenu: MainMenu private set

    override fun create() {
        view = StretchViewport(gameWidth, gameHeight)

        mainMenu = MainMenu(this)
        settingsMenu = SettingsMenu(this)
        screen = settingsMenu
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        screen.render(0f)
    }

    override fun resize(width: Int, height: Int) {
        view.update(width, height, true)
    }

    override fun dispose() {
        settingsMenu.dispose()
        mainMenu.dispose()
    }

    fun updateLang() {
        settingsMenu.update()
        mainMenu.update()
    }
}
