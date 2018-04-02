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
import game.poker.staticFiles.Textures
import game.poker.staticFiles.Fonts
import game.poker.gui.ScrollableContainer
import game.poker.gui.ScrollableContainer.ClickHandler
import game.poker.gui.TournamentItem
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class TournamentMenu(val game: PocketPoker) : BaseScreen {

    private val stage = Stage(game.view)
    private val PADDING = 50f
    private val tournamentsList: ScrollableContainer
    private val createButton: TextButton
    private val mainMenuButton: TextButton
    private val lock: Lock = ReentrantLock()
    private var tournamentsData = JsonArray()

    init {

        tournamentsList = ScrollableContainer(object : ClickHandler() {
            override fun click(itemId: Int) {
                println(itemId.toString() + " id clicked")
            }
        })

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

        createButton = TextButton(Settings.getText(Settings.TextKeys.CREATE_TOURNAMENT), buttonStyle)
        createButton.addListener(game.switches[ScreenType.CREATE_TOURNAMENT])
        table.add(createButton).pad(PADDING).expandX().fillX().height(100f).row()
        table.add(TextField("", editStyle)).pad(PADDING).expandX().fillX().row()
        table.add(tournamentsList.actor).expandX().fillX().row()
        mainMenuButton = TextButton(Settings.getText(Settings.TextKeys.MAIN_MENU), buttonStyle)
        mainMenuButton.addListener(game.switches[ScreenType.MAIN_MENU])
        table.add(mainMenuButton).pad(PADDING).expand().left().bottom()
        stage.addActor(table)

    }

    override fun update(){
        createButton.setText(Settings.getText(Settings.TextKeys.CREATE_TOURNAMENT))
        mainMenuButton.setText(Settings.getText(Settings.TextKeys.MAIN_MENU))
    }

    override fun show(){
        lock.withLock {
            Gdx.input.inputProcessor = stage
            sendRequestForTournamentsUpdate()
        }
    }

    override fun render(delta: Float) {
        lock.withLock {
            if (tournamentsData.size() != 0) updateTournaments()
            stage.act()
            stage.draw()
        }
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
        lock.withLock {
            if (json["type"].asString == "get tournaments") {
                tournamentsData = json["info"].asJsonArray
            }
        }
    }

    private fun sendRequestForTournamentsUpdate() {
        val data = JsonObject()
        data.addProperty("type", "get tournaments")
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
            if (name == "") name = Settings.getText(Settings.TextKeys.TOURNAMENT) + " #" + id.toString()
            tournamentsList.add(TournamentItem(id, name, playersLeft, players, stack, isStarted = started))
        }
        tournamentsData = JsonArray()
    }
}
