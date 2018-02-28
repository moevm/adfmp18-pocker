package game.poker

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.badlogic.gdx.scenes.scene2d.InputEvent
import game.poker.gui.Chipstack

import game.poker.screens.*

class PocketPoker : Game() {

    val gameWidth = 1080f
    val gameHeight = 1920f

    lateinit var view: Viewport private set
    lateinit var screens: Map<ScreenType, BaseScreen> private set
    lateinit var switches: Map<ScreenType, ClickListener> private set

    override fun create() {
        view = StretchViewport(gameWidth, gameHeight)

        fun switchTo(screen: ScreenType) = object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                this@PocketPoker.setCurrScreen(screen)
            }
        }

        val switches = mutableMapOf<ScreenType, ClickListener>()
        ScreenType.values().forEach { switches[it] = switchTo(it) }
        this.switches = switches

        val screens = mutableMapOf<ScreenType, BaseScreen>()
        screens[ScreenType.MAIN_MENU] = MainMenu(this)
        screens[ScreenType.SETTINGS] = SettingsMenu(this)
        this.screens = screens

        setCurrScreen(ScreenType.MAIN_MENU)
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        screen.render(0f)
    }

    override fun resize(width: Int, height: Int) {
        view.update(width, height, true)
    }

    fun setCurrScreen(type: ScreenType) {
        screen = screens[type]
        screen.show()
    }

    override fun dispose() {
        screens.forEach { it.value.dispose() }
        Gdx.app.exit()
    }

    fun updateLang() {
        screens.forEach { it.value.update() }
    }
}
