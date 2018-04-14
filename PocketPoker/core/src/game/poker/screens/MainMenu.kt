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
import com.google.gson.JsonObject

import game.poker.PocketPoker
import game.poker.Settings
import game.poker.staticFiles.*
import game.poker.staticFiles.Texts.TextKeys
import java.util.*


class MainMenu(val game: PocketPoker) : BaseScreen {

    private val stage = Stage(game.view)
    private val PADDING = 50f
    private val ICON_SIZE = 100f
    private var isRandomRegister = false
    private var isUserSet = false
    private var connectedToServer = false

    private val nickLabel: Label
    private val nickEdit: TextField
    private val nickEditButton: TextButton
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
        val nextSprite = SpriteDrawable(Sprite(Textures.next))

        val logoStyle = LabelStyle(Fonts.mainMenuLogoFont, Color.BLACK)
        val labelStyle = LabelStyle(Fonts.mainMenuButtonFont, Color.BLACK)
        val buttonStyle = TextButtonStyle(buttonSprite, buttonDownSprite, buttonSprite,
                Fonts.mainMenuButtonFont)
        val nextButtonStyle = TextButtonStyle(nextSprite, nextSprite, nextSprite,
                Fonts.mainMenuButtonFont)
        val editStyle = TextFieldStyle(Fonts.mainMenuButtonFont, Color.BLACK, editCursorSprite,
                editSprite, editSprite)

        val table = Table()
        table.pad(PADDING)
        table.setFillParent(true)
        table.align(Align.center)

        val titleLabel = Label(Texts[TextKeys.POCKET_POKER], logoStyle)
        table.add(titleLabel).colspan(2).pad(PADDING).expand().fill().center().row()

        nickLabel = Label(Texts[TextKeys.NICK], labelStyle)
        table.add(nickLabel).expandX().fillX().padLeft(PADDING).row()

        nickEdit = TextField("", editStyle)
        table.add(nickEdit).pad(PADDING).expandX().fillX()

        nickEditButton = TextButton("", nextButtonStyle)
        nickEditButton.addListener(object: ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                nickEditButton.isDisabled = true
                nickEdit.isDisabled = true
                changeNick()
            }
        })
        table.add(nickEditButton).height(ICON_SIZE).width(ICON_SIZE).padRight(PADDING).row()

        fun addButtonToTable(textKey: TextKeys): TextButton {
            val button = TextButton(Texts[textKey], buttonStyle)
            table.add(button).colspan(2).pad(PADDING).expand().fill().row()
            return button
        }

        quickGameButton = addButtonToTable(TextKeys.QUICK_GAME)
        tournamentButton = addButtonToTable(TextKeys.TOURNAMENT)
        archiveButton = addButtonToTable(TextKeys.ARCHIVE)
        settingsButton = addButtonToTable(TextKeys.SETTINGS)
        exitButton = addButtonToTable(TextKeys.EXIT)

        tournamentButton.addListener(game.switches[ScreenType.TOURNAMENT])
        archiveButton.addListener(game.switches[ScreenType.ARCHIVE])
        quickGameButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                val data = JsonObject()
                data.addProperty("type", "create quick game")
                data.addProperty("name", Settings.nick)
                game.menuHandler.sendToServer(data)
            }
        })
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
        if (!isUserSet && connectedToServer) setUser()
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
        nickLabel.setText(Texts[TextKeys.NICK])
        quickGameButton.setText(Texts[TextKeys.QUICK_GAME])
        tournamentButton.setText(Texts[TextKeys.TOURNAMENT])
        archiveButton.setText(Texts[TextKeys.ARCHIVE])
        settingsButton.setText(Texts[TextKeys.SETTINGS])
        exitButton.setText(Texts[TextKeys.EXIT])
    }

    override fun receiveFromServer(json: JsonObject) {

        if (json["type"].asString == "register") {
            if (json["answer"].asString == "success") {
                isRandomRegister = false
                isUserSet = true
                nickEditButton.isDisabled = false
                nickEdit.isDisabled = false
                Settings.nick = nickEdit.text
                Settings.token = json["token"].asString
                val prefs = Gdx.app.getPreferences("main")
                prefs.putString("nick", Settings.nick)
                prefs.putString("token", Settings.token)
            } else {
                if (isRandomRegister) {
                    nickEdit.text = generateNick()
                    registerUser()
                }
                else {
                    nickEditButton.isDisabled = false
                    nickEdit.isDisabled = false
                    alert(Texts[TextKeys.REGISTRATION_ERROR],
                            Texts[TextKeys.NICK_IS_USED])
                }
            }
        }
        else if (json["type"].asString == "check token") {
            if (json["answer"].asString != "success") {
                nickEdit.isDisabled = true
                nickEditButton.isDisabled = true
                nickEdit.text = generateNick()
                registerUser()
            } else {
                isUserSet = true
                nickEdit.isDisabled = false
                nickEditButton.isDisabled = false
            }
        }
        else if(json["type"].asString == "change name") {

            nickEdit.isDisabled = false
            nickEditButton.isDisabled = false

            if (json["answer"].asString == "success") {
                isUserSet = true
                Settings.nick = nickEdit.text
                Settings.token = json["token"].asString
                val prefs = Gdx.app.getPreferences("main")
                prefs.putString("nick",Settings.nick)
                prefs.putString("token", Settings.token)
            }
            else if (json["answer"].asString == "fail") {
                nickEdit.text = Settings.nick
                alert(Texts[TextKeys.REGISTRATION_ERROR],
                        Texts[TextKeys.NICK_IS_USED])
            }
        }
        else if(json["type"].asString == "quick game is ready"){
            Settings.currTableMode = Settings.TableMode.Game
            Settings.currTournamentId = -1
            game.setCurrScreen(ScreenType.TABLE)
        }
        else if(json["type"].asString == "connected"){
            connectedToServer = true
            setUser()
        }
    }

    private fun generateNick(): String {
        val rand = Random()
        return "User #" + rand.nextInt(1000000).toString()
    }

    private fun registerUser() {
        val data = JsonObject()
        data.addProperty("type", "register name")
        data.addProperty("name", nickEdit.text)
        game.menuHandler.sendToServer(data)
    }

    private fun checkToken(nick: String, token: String) {
        val data = JsonObject()
        data.addProperty("type", "check token")
        data.addProperty("name", nick)
        data.addProperty("token", token)
        game.menuHandler.sendToServer(data)
    }

    private fun alert(title: String, text: String) {
        val windowStyle = Window.WindowStyle()
        windowStyle.titleFont = Fonts.mainMenuButtonFont
        windowStyle.titleFontColor = Color.BLACK
        val dialog = Dialog(title, windowStyle)
        //dialog.button("OK", true)
        dialog.text(text)
        dialog.show(stage)
    }

    private fun setUser() {

        val prefs = Gdx.app.getPreferences("main")
        var nick = prefs.getString("nick", "")
        var token = prefs.getString("token", "")

        //dummy data
        nick = "Владимир"
        token = "4I23z0Dg2g1l5mcldKlOba410ajHmQI1uayFY97HWon5LDb0iNn0ZQ22jUSlGDN2"

        Settings.nick = nick
        Settings.token = token
        nickEditButton.isDisabled = true
        nickEdit.isDisabled = true

        if (nick == "") {
            isRandomRegister = true
            nickEdit.text = generateNick()
            registerUser()
        } else {
            nickEdit.text = nick
            checkToken(nick, token)
        }

    }

    fun changeNick() {
        val data = JsonObject()
        data.addProperty("type", "change name")
        data.addProperty("old name", Settings.nick)
        data.addProperty("new name", nickEdit.text)
        data.addProperty("token", Settings.token)
        game.menuHandler.sendToServer(data)
    }

    override fun setPreviousScreen() {

    }

}
