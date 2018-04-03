package game.poker.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.List
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.google.gson.JsonObject

import game.poker.PocketPoker
import game.poker.Settings
import game.poker.staticFiles.Textures
import game.poker.staticFiles.Fonts


class CreateTournamentScreen(val game: PocketPoker) : BaseScreen {

    private val stage = Stage(game.view)
    private val PADDING = 20f
    private val titleLabel: Label
    private val playersLabel: Label
    private val botsLabel: Label
    private val tablePlayersLabel: Label
    private val chipsLabel: Label
    private val blindSpeedLabel: Label
    private val startBlindsLabel: Label
    private val titleEdit: TextField
    private val playersEdit: TextField
    private val tablePlayersEdit: SelectBox<String>
    private val botsEdit: TextField
    private val chipsEdit: TextField
    private val blindSpeedEdit: SelectBox<String>
    private val startBlindsEdit: SelectBox<String>
    private val toTournamentsButton: TextButton
    private val createButton: TextButton
    //private val passwordLabel: Label
    //private val passwordEdit: TextField
    private val blindSpeedValues = arrayOf("standard", "fast", "rapid", "bullet")

    init {

        stage.addActor(Image(Textures.menuBg))

        val buttonSprite = SpriteDrawable(Sprite(Textures.menuButton))
        val buttonDownSprite = SpriteDrawable(Sprite(Textures.menuButtonDown))
        val selectBoxSprite = SpriteDrawable(Sprite(Textures.scroll))

        val labelStyle = LabelStyle(Fonts.mainMenuLabelFont, Color.BLACK)
        val buttonStyle = TextButtonStyle(buttonSprite, buttonDownSprite, buttonSprite,
                Fonts.mainMenuButtonFont)
        val editSprite = SpriteDrawable(Sprite(Textures.edit))
        val editCursorSprite = SpriteDrawable(Sprite(Textures.editCursor))
        val editStyle = TextField.TextFieldStyle(Fonts.mainMenuButtonFont, Color.BLACK, editCursorSprite,
                editSprite, editSprite)
        val selectBoxStyle = SelectBox.SelectBoxStyle(Fonts.mainMenuLabelFont,
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
        )

        val table = Table()
        table.pad(PADDING)
        table.setFillParent(true)

        titleLabel = Label(Settings.getText(Settings.TextKeys.TITLE), labelStyle)
        table.add(titleLabel).pad(PADDING).colspan(2).fillX().expandX().left().row()
        titleEdit = TextField("", editStyle)
        table.add(titleEdit).pad(PADDING).colspan(2).fillX().expandX().left().row()

        playersLabel = Label(Settings.getText(Settings.TextKeys.PLAYERS), labelStyle)
        table.add(playersLabel).pad(PADDING).expandX().fillX().left()

        botsLabel = Label(Settings.getText(Settings.TextKeys.BOTS), labelStyle)
        table.add(botsLabel).pad(PADDING).expandX().fillX().left().row()

        playersEdit = TextField("9", editStyle)
        playersEdit.textFieldFilter = TextFieldFilter.DigitsOnlyFilter()
        table.add(playersEdit).pad(PADDING).expandX().fillX().left()

        botsEdit = TextField("0", editStyle)
        botsEdit.textFieldFilter = TextFieldFilter.DigitsOnlyFilter()
        table.add(botsEdit).pad(PADDING).fillX().expandX().left().row()

        tablePlayersLabel = Label(Settings.getText(Settings.TextKeys.TABLE_PLAYERS), labelStyle)
        table.add(tablePlayersLabel).pad(PADDING).fillX().expandX().left()

        tablePlayersEdit = SelectBox<String>(selectBoxStyle)
        tablePlayersEdit.setItems("2", "3", "4", "5", "6", "7", "8", "9")
        tablePlayersEdit.setAlignment(Align.center)
        table.add(tablePlayersEdit).pad(PADDING).align(Align.center).row()

        chipsLabel = Label(Settings.getText(Settings.TextKeys.CHIPS), labelStyle)
        table.add(chipsLabel).pad(PADDING).fillX().expandX().left()
        chipsEdit = TextField("10000", editStyle)
        chipsEdit.textFieldFilter = TextFieldFilter.DigitsOnlyFilter()
        table.add(chipsEdit).pad(PADDING).fillX().expandX().left().row()

        blindSpeedLabel = Label(Settings.getText(Settings.TextKeys.BLIND_SPEED), labelStyle)
        table.add(blindSpeedLabel).pad(PADDING).colspan(2).fillX().expandX().left().row()

        blindSpeedEdit = SelectBox<String>(selectBoxStyle)
        blindSpeedEdit.setItems(Settings.getText(Settings.TextKeys.STANDARD),
                Settings.getText(Settings.TextKeys.FAST),
                Settings.getText(Settings.TextKeys.RAPID),
                Settings.getText(Settings.TextKeys.BULLET)
        )
        table.add(blindSpeedEdit).pad(PADDING).colspan(2).fillX().expandX().left().row()

        startBlindsLabel = Label(Settings.getText(Settings.TextKeys.START_BLINDS), labelStyle)
        table.add(startBlindsLabel).pad(PADDING).colspan(2).fillX().expandX().left().row()

        startBlindsEdit = SelectBox<String>(selectBoxStyle)
        startBlindsEdit.setItems("5/10, 0", "60/120, 0", "500/1000, 100")
        table.add(startBlindsEdit).pad(PADDING).colspan(2).fillX().expandX().left().row()

        //passwordLabel = Label(Settings.getText(Settings.TextKeys.PASSWORD), labelStyle)
        //table.add(passwordLabel).pad(PADDING).colspan(2).fillX().expandX().left().row()
        //passwordEdit = TextField("", editStyle)
        //table.add(passwordEdit).pad(PADDING).colspan(2).fillX().expandX().left().row()

        toTournamentsButton = TextButton(Settings.getText(Settings.TextKeys.TOURNAMENT), buttonStyle)
        toTournamentsButton.addListener(game.switches[ScreenType.TOURNAMENT])
        table.add(toTournamentsButton).pad(PADDING).expand().pad(PADDING).left().bottom()
        createButton = TextButton(Settings.getText(Settings.TextKeys.CREATE), buttonStyle)
        createButton.addListener(object: ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                createTournament()
            }
        })
        table.add(createButton).pad(PADDING).expand().pad(PADDING).right().bottom()

        stage.addActor(table)
    }

    override fun update(){
        titleLabel.setText(Settings.getText(Settings.TextKeys.TITLE))
        playersLabel.setText(Settings.getText(Settings.TextKeys.PLAYERS))
        tablePlayersLabel.setText(Settings.getText(Settings.TextKeys.TABLE_PLAYERS))
        botsLabel.setText(Settings.getText(Settings.TextKeys.BOTS))
        chipsLabel.setText(Settings.getText(Settings.TextKeys.CHIPS))
        blindSpeedLabel.setText(Settings.getText(Settings.TextKeys.BLIND_SPEED))
        startBlindsLabel.setText(Settings.getText(Settings.TextKeys.START_BLINDS))
        //passwordLabel.setText(Settings.getText(Settings.TextKeys.PASSWORD))
        toTournamentsButton.setText(Settings.getText(Settings.TextKeys.TOURNAMENT))
        createButton.setText(Settings.getText(Settings.TextKeys.CREATE))
        blindSpeedEdit.setItems(Settings.getText(Settings.TextKeys.STANDARD),
                Settings.getText(Settings.TextKeys.FAST),
                Settings.getText(Settings.TextKeys.RAPID),
                Settings.getText(Settings.TextKeys.BULLET)
        )
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

    override fun receiveFromServer(json: JsonObject) {

    }

    private fun createTournament() {
        val data = JsonObject()
        data.addProperty("type", "create tournament")
        data.addProperty("name", titleEdit.text)
        data.addProperty("total count", playersEdit.text.toInt())
        data.addProperty("bot count", botsEdit.text.toInt())
        data.addProperty("chip count", chipsEdit.text.toInt())
        data.addProperty("players", tablePlayersEdit.selected.toInt())
        data.addProperty("blinds speed", blindSpeedValues[blindSpeedEdit.selectedIndex])
        data.addProperty("start blinds", startBlindsEdit.selectedIndex + 1)
        data.addProperty("password", "")
        game.menuHandler.sendToServer(data)
        Thread.sleep(1000)
        game.setCurrScreen(ScreenType.TOURNAMENT)
    }

    private fun alert(title: String, message: String) {
        val style = Window.WindowStyle(Fonts.mainMenuLabelFont, Color.BLACK, SpriteDrawable(Sprite(Textures.labelBg)))
        val dialog = Dialog(title, style)
        dialog.pad(PADDING)
        dialog.contentTable.align(Align.center)
        val text = Label(message, LabelStyle(Fonts.mainMenuLabelFont, Color.BLACK))
        text.setAlignment(Align.center)
        dialog.text(text)
        val buttonSprite = SpriteDrawable(Sprite(Textures.menuButton))
        val buttonDownSprite = SpriteDrawable(Sprite(Textures.menuButtonDown))
        val buttonStyle = TextButtonStyle(buttonSprite, buttonDownSprite, buttonSprite, Fonts.mainMenuButtonFont)
        dialog.button(TextButton("OK", buttonStyle))
        dialog.show(stage)
    }

}