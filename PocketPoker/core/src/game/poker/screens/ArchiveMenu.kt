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
import game.poker.gui.menu.*
import game.poker.gui.menu.ScrollableContainer.ClickHandler

class ArchiveMenu(val game: PocketPoker) : BaseScreen {

    private val stage = Stage(game.view)
    private val PADDING = 50f
    private val archiveList = ScrollableContainer()
    private val mainMenuButton: TextButton
    private var archiveData = JsonArray()

    init {

        val clickHandler = object: ClickHandler() {
            override fun click(itemId: Int) {
                Settings.currArchiveTournamentId = itemId
                game.setCurrScreen(ScreenType.ARCHIVE_TABLE_LIST)
            }
        }
        archiveList.clickHandler = clickHandler

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

        table.add(TextField("", editStyle)).pad(PADDING).expandX().fillX().row()
        table.add(archiveList.actor).expandX().fillX().row()
        mainMenuButton = TextButton(Settings.getText(Settings.TextKeys.MAIN_MENU), buttonStyle)
        mainMenuButton.addListener(game.switches[ScreenType.MAIN_MENU])
        table.add(mainMenuButton).pad(PADDING).expand().left().bottom()
        stage.addActor(table)

    }

    override fun update(){
        mainMenuButton.setText(Settings.getText(Settings.TextKeys.MAIN_MENU))
    }

    override fun show(){
        Gdx.input.inputProcessor = stage
        sendRequestForUpdateArchive()
    }

    override fun render(delta: Float) {
        stage.act()
        stage.draw()
        if (archiveData.size() != 0) updateArchive()
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
        if (json["type"].asString == "replays") {
            archiveData = json["info"].asJsonArray
        }
    }

    private fun updateArchive() {
        archiveList.clear()
        for (field in archiveData) {
            val item = field.asJsonObject
            val id = item["id"].asInt
            val date = item["date"].asString.split("_")[0]
            val tables = item["tables"].asInt
            val players = item["players"].asInt
            val hands = item["hands"].asInt
            var name = item["name"].asString
            if (name == "") name = Settings.getText(Settings.TextKeys.TOURNAMENT) + " #" + id.toString()
            archiveList.add(ArchiveItem(id, name, date, tables, players, hands))
        }
        archiveData = JsonArray()
    }

    private fun sendRequestForUpdateArchive() {
        val data = JsonObject()
        data.addProperty("type", "get replays")
        game.menuHandler.sendToServer(data)
    }
}
