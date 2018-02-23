package game.poker.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.List
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.utils.Align

import game.poker.PocketPoker
import game.poker.Settings
import game.poker.staticFiles.Textures
import game.poker.staticFiles.Fonts


class SettingsMenu(val game: PocketPoker) : Screen {

    private val stage = Stage(game.view)
    private val PADDING = 20f

    init {

        stage.addActor(Image(Textures.menuBg))

        val buttonSprite = SpriteDrawable(Sprite(Textures.menuButton))
        val buttonDownSprite = SpriteDrawable(Sprite(Textures.menuButtonDown))
        val editSprite = SpriteDrawable(Sprite(Textures.edit))
        val selectBoxSprite = SpriteDrawable(Sprite(Textures.scroll))

        val labelStyle = LabelStyle(Fonts.mainMenuLabelFont, Color.BLACK)
        val buttonStyle = TextButtonStyle(buttonSprite, buttonDownSprite, buttonSprite,
                Fonts.mainMenuButtonFont)

        val table = Table()
        table.pad(PADDING)
        table.setFillParent(true)
        table.align(Align.center)

        val soundLabel = Label(Settings.getText(Settings.TextKeys.SOUND_LEVEL), labelStyle)
        table.add(soundLabel).colspan(2).pad(PADDING).row()

        val musicLabel = Label(Settings.getText(Settings.TextKeys.MUSIC_LEVEL), labelStyle)
        table.add(musicLabel).colspan(2).pad(PADDING).row()

        val languageLabel = Label(Settings.getText(Settings.TextKeys.LANGUAGE), labelStyle)
        table.add(languageLabel).pad(PADDING)

        val languageSelect = SelectBox<String>(SelectBox.SelectBoxStyle(Fonts.mainMenuLabelFont,
                Color.BLACK,
                selectBoxSprite,
                ScrollPane.ScrollPaneStyle(SpriteDrawable(Sprite(Textures.scrollBg)),
                        SpriteDrawable(Sprite(Textures.hScroll)),
                        SpriteDrawable(Sprite(Textures.hScrollKnob)),
                        SpriteDrawable(Sprite(Textures.vScroll)),
                        SpriteDrawable(Sprite(Textures.vScrollKnob))),
                List.ListStyle(Fonts.mainMenuLabelFont,
                        Color.BLACK,
                        Color.GRAY,
                        SpriteDrawable(Sprite(Textures.list_selection)))
                 ))
        val str = arrayOf("First","Second","Third")
        languageSelect.setItems(com.badlogic.gdx.utils.Array<String>(str))
        table.add(languageSelect).pad(PADDING).row()

        val cardsLabel = Label(Settings.getText(Settings.TextKeys.CARDS), labelStyle)
        table.add(cardsLabel).pad(PADDING).row()

        val mainMenuButton = TextButton(Settings.getText(Settings.TextKeys.MAIN_MENU), buttonStyle)
        table.add(mainMenuButton).colspan(2).pad(PADDING).fill()

        stage.addActor(table)
        Gdx.input.inputProcessor = stage
    }

    override fun show(){
        println("SettingsMenu show")
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
