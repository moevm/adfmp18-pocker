package game.poker.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.utils.Align

import game.poker.PocketPoker
import game.poker.Settings
import game.poker.staticFiles.Textures
import game.poker.staticFiles.Fonts


class MainMenu(val game: PocketPoker) : BaseScreen {

    private val stage = Stage(game.view)
    private val PADDING = 50f

    private val nickLabel: Label
    private val quickGameButton: TextButton
    private val tournamentButton: TextButton
    private val archiveButton: TextButton
    private val settingsButton: TextButton
    private val exitButton: TextButton

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

        nickLabel = Label(Settings.getText(Settings.TextKeys.NICK), labelStyle)
        table.add(nickLabel).pad(PADDING)

        val nickEdit = TextField("", editStyle)
        table.add(nickEdit).pad(PADDING).expand().fill().row()

        fun addButtonToTable(textKey: Settings.TextKeys): TextButton {
            val button = TextButton(Settings.getText(textKey), buttonStyle)
            table.add(button).colspan(2).pad(PADDING).expand().fill().row()
            return button
        }

        quickGameButton = addButtonToTable(Settings.TextKeys.QUICK_GAME)
        tournamentButton = addButtonToTable(Settings.TextKeys.TOURNAMENT)
        archiveButton = addButtonToTable(Settings.TextKeys.ARCHIVE)
        settingsButton = addButtonToTable(Settings.TextKeys.SETTINGS)
        exitButton = addButtonToTable(Settings.TextKeys.EXIT)

        tournamentButton.addListener(game.switches[ScreenType.TOURNAMENT])
        archiveButton.addListener(game.switches[ScreenType.ARCHIVE])
        quickGameButton.addListener(game.switches[ScreenType.TABLE])
        settingsButton.addListener(game.switches[ScreenType.SETTINGS])
        exitButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                game.dispose()
            }
        })

        stage.addActor(table)
    }

    override fun show(){
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        stage.act()
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

    override fun update() {
        nickLabel.setText(Settings.getText(Settings.TextKeys.NICK))
        quickGameButton.setText(Settings.getText(Settings.TextKeys.QUICK_GAME))
        tournamentButton.setText(Settings.getText(Settings.TextKeys.TOURNAMENT))
        archiveButton.setText(Settings.getText(Settings.TextKeys.ARCHIVE))
        settingsButton.setText(Settings.getText(Settings.TextKeys.SETTINGS))
        exitButton.setText(Settings.getText(Settings.TextKeys.EXIT))
    }
}
