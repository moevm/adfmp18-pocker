package game.poker.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.utils.Align

import game.poker.PocketPoker
import game.poker.Settings
import game.poker.staticFiles.Textures
import game.poker.staticFiles.Fonts


class MainMenu(val game: PocketPoker) : Screen {

    private val stage = Stage(game.view)
    private val PADDING = 50f

    init {

        stage.addActor(Image(Textures.menuBg))

        val buttonSprite = SpriteDrawable(Sprite(Textures.menuButton))
        val buttonDownSprite = SpriteDrawable(Sprite(Textures.menuButtonDown))
        val editSprite = SpriteDrawable(Sprite(Textures.edit))
        val editCursorSprite = SpriteDrawable(Sprite(Textures.editCursor))

        val logoStyle = LabelStyle(Fonts.mainMenuLogoFont, Color.BLACK)
        val labelStyle = LabelStyle(Fonts.mainMenuButtonFont, Color.BLACK)
        val buttonStyle = TextButtonStyle(buttonSprite, buttonDownSprite, buttonSprite,
                Fonts.mainMenuButtonFont)
        val editStyle = TextFieldStyle(Fonts.mainMenuButtonFont, Color.BLACK, editCursorSprite,
                editSprite, editSprite)

        val table = Table()
        table.pad(PADDING)
        table.setFillParent(true)
        table.align(Align.center)

        val titleLabel = Label(Settings.getText(Settings.TextKeys.POCKET_POKER), logoStyle)
        table.add(titleLabel).colspan(2).pad(PADDING).expand().fill().center().row()

        val nickLabel = Label(Settings.getText(Settings.TextKeys.NICK), labelStyle)
        table.add(nickLabel).pad(PADDING)

        val nickEdit = TextField("", editStyle)
        table.add(nickEdit).pad(PADDING).expand().fill().row()

        fun addButtonToTable(textKey: Settings.TextKeys) {
            val button = TextButton(Settings.getText(textKey), buttonStyle)
            table.add(button).colspan(2).pad(PADDING).expand().fill().row()
        }

        addButtonToTable(Settings.TextKeys.QUICK_GAME)
        addButtonToTable(Settings.TextKeys.TOURNAMENT)
        addButtonToTable(Settings.TextKeys.ARCHIVE)
        addButtonToTable(Settings.TextKeys.SETTINGS)
        addButtonToTable(Settings.TextKeys.EXIT)

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
    fun update() {
        //TODO: implement
    }
}
