package game.poker

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.badlogic.gdx.utils.viewport.Viewport

import game.poker.screens.MainMenu
import game.poker.screens.SettingsMenu
import game.poker.staticFiles.Textures

class PocketPoker : Game() {

    val gameWidth = 1080f
    val gameHeight = 1920f

    lateinit var batch: SpriteBatch
    lateinit var img: Sprite
    lateinit var view: Viewport
    lateinit var settingsMenu:SettingsMenu
    lateinit var mainMenu:MainMenu

    override fun create() {
        batch = SpriteBatch()
        view = StretchViewport(gameWidth, gameHeight)

        mainMenu = MainMenu(this)
        settingsMenu = SettingsMenu(this)
        screen = settingsMenu

        img = Sprite(Textures.test)
        img.setBounds(200f, 200f, 400f, 400f)
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.projectionMatrix = view.camera.combined
        batch.begin()
        screen.render(0f)
        //img.draw(batch)
        batch.end()
    }

    override fun resize(width: Int, height: Int) {
        view.update(width, height, true)
    }

    override fun dispose() {
        batch.dispose()
        //img.dispose()
    }

    fun updateLang() {
        settingsMenu.update()
        mainMenu.update()
    }
}
