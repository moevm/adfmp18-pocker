package game.poker.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.utils.Align

import game.poker.PocketPoker
import game.poker.Settings
import game.poker.staticFiles.Textures
import game.poker.staticFiles.Fonts


class MainMenu(val game: PocketPoker) : Screen {

    private val stage = Stage(game.view)

    init {

        stage.addActor(Image(Textures.mainMenuBg))

        val buttonSprite = SpriteDrawable(Sprite(Textures.menuButton))
        val buttonDownSprite = SpriteDrawable(Sprite(Textures.menuButtonDown))
        val buttonStyle = TextButtonStyle(buttonSprite, buttonDownSprite, buttonSprite,
                Fonts.mainMenuButtonFont)
        val labelStyle = LabelStyle(Fonts.mainMenuLogoFont, Color.BLACK)

        val table = Table()
        table.width = stage.width
        table.align(Align.center)
        table.setPosition(0f, game.gameHeight / 2f)

        val titleLabel = Label(Settings.getText(Settings.TextKeys.POKCET_POKER), labelStyle)
        table.add(titleLabel).padBottom(10f).row()

        val startButton = TextButton(Settings.getText(Settings.TextKeys.QUICK_GAME), buttonStyle)
        table.add(startButton).padBottom(10f).row()

        val tournamentButton = TextButton(Settings.getText(Settings.TextKeys.TOURNAMENT), buttonStyle)
        table.add(tournamentButton).padBottom(10f).row()

        val archiveButton = TextButton(Settings.getText(Settings.TextKeys.ARCHIVE), buttonStyle)
        table.add(archiveButton).padBottom(10f).row()

        val settingsButton = TextButton(Settings.getText(Settings.TextKeys.SETTINGS), buttonStyle)
        table.add(settingsButton).padBottom(10f).row()

        val exitButton = TextButton(Settings.getText(Settings.TextKeys.EXIT), buttonStyle)
        table.add(exitButton)

        stage.addActor(table)
        Gdx.input.inputProcessor = stage
    }

    override fun show(){
        println("MainMenu show")
    }

    override fun render(delta: Float) {
        stage.draw()
    }

    override fun resize(width: Int, height: Int){

    }

    override fun pause(){

    }

    override fun resume(){

    }

    override fun hide(){

    }

    override fun dispose(){

    }
}