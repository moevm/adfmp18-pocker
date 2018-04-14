package game.poker.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.google.gson.JsonArray
import com.google.gson.JsonObject

import game.poker.PocketPoker
import game.poker.Settings
import game.poker.staticFiles.*
import game.poker.staticFiles.Texts.TextKeys
import game.poker.gui.menu.ScrollableContainer
import game.poker.gui.menu.ScrollableContainer.ClickHandler
import game.poker.gui.menu.TournamentItem


class TournamentMenu(val game: PocketPoker) : BaseScreen {

    private val stage = Stage(game.view)
    private val PADDING = 50f
    private val tournamentsList = ScrollableContainer()
    private val createButton: TextButton
    private val mainMenuButton: TextButton
    private val searchEdit: TextField
    private var tournamentsData = JsonArray()
    private var needUpadte = false

    init {

        val clickHandler = object : ClickHandler() {
            override fun click(itemId: Int, isGameMode: Boolean) {
                val item = tournamentsList.get(itemId) as TournamentItem
                Settings.currTournamentId = itemId
                if (item.isStarted) {
                    if (isGameMode){
                        Settings.currTableMode = Settings.TableMode.Game
                        game.setCurrScreen(ScreenType.TABLE)
                    }
                    else {
                        game.setCurrScreen(ScreenType.TOURNAMENT_TABLE_LIST)
                    }
                } else {
                    Settings.currTableMode = Settings.TableMode.Game
                    game.setCurrScreen(ScreenType.TABLE)
                }
            }
        }
        tournamentsList.clickHandler = clickHandler

        val buttonSprite = SpriteDrawable(Sprite(Textures.menuButton))
        val buttonDownSprite = SpriteDrawable(Sprite(Textures.menuButtonDown))
        val buttonStyle = TextButtonStyle(buttonSprite, buttonDownSprite, buttonSprite,
                Fonts.mainMenuButtonFont)
        val editSprite = SpriteDrawable(Sprite(Textures.edit))
        val editCursorSprite = SpriteDrawable(Sprite(Textures.editCursor))
        val editStyle = TextField.TextFieldStyle(Fonts.mainMenuButtonFont, Color.BLACK, editCursorSprite,
                editSprite, editSprite)

        stage.addActor(Image(Textures.menuBg))

        val table = Table()
        table.pad(PADDING)
        table.setFillParent(true)
        table.top()

        createButton = TextButton(Texts[TextKeys.CREATE_TOURNAMENT], buttonStyle)
        createButton.addListener(game.switches[ScreenType.CREATE_TOURNAMENT])
        table.add(createButton).pad(PADDING).expandX().fillX().height(100f).row()
        searchEdit = TextField("", editStyle)
        searchEdit.setTextFieldListener { textField, key -> updateTournaments()}
        table.add(searchEdit).pad(PADDING).expandX().fillX().row()
        table.add(tournamentsList.actor).expandX().fillX().row()
        mainMenuButton = TextButton(Texts[TextKeys.MAIN_MENU], buttonStyle)
        mainMenuButton.addListener(game.switches[ScreenType.MAIN_MENU])
        table.add(mainMenuButton).pad(PADDING).expand().left().bottom()
        stage.addActor(table)

    }

    override fun update(){
        createButton.setText(Texts[TextKeys.CREATE_TOURNAMENT])
        mainMenuButton.setText(Texts[TextKeys.MAIN_MENU])
    }

    override fun show(){
        Gdx.input.inputProcessor = stage
        sendRequestForTournamentsUpdate()
    }

    override fun render(delta: Float) {
        if (needUpadte) updateTournaments()
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
        tournamentsList.clear()
    }

    override fun dispose(){

    }

    override fun receiveFromServer(json: JsonObject) {
        if (json["type"].asString == "get tournaments") {
            tournamentsData = json["info"].asJsonArray
            needUpadte = true
        }
    }

    private fun sendRequestForTournamentsUpdate() {
        val data = JsonObject()
        data.addProperty("type", "get tournaments")
        data.addProperty("name", Settings.nick)
        data.addProperty("token", Settings.token)
        game.menuHandler.sendToServer(data)
    }

    private fun updateTournaments() {
        tournamentsList.clear()
        for (field in tournamentsData) {
            val item = field.asJsonObject
            val id = item["id"].asInt
            var name = item["name"].asString
            val players = item["total players"].asInt
            val playersLeft = item["players left"].asInt
            val stack = item["initial stack"].asInt
            val started = item["started"].asBoolean
            val canPlay = item["can play"].asBoolean
            if (name == "") name = Texts[TextKeys.TOURNAMENT] + " #" + id.toString()
            if (name.contains(searchEdit.text, true))
                tournamentsList.add(TournamentItem(id, name, playersLeft, players, stack,
                        isStarted = started, canPlay = canPlay))
        }
        needUpadte = false
    }

    override fun setPreviousScreen() {
        println("Set prev screen to main from tournament")
        game.setCurrScreen(ScreenType.MAIN_MENU)
    }
}
