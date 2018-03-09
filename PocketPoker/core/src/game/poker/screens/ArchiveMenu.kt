package game.poker.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.google.gson.JsonObject

import game.poker.PocketPoker
import game.poker.Settings
import game.poker.staticFiles.Textures
import game.poker.staticFiles.Fonts
import game.poker.gui.ScrollableContainer
import game.poker.gui.ScrollableContainer.ClickHandler
import game.poker.gui.ArchiveItem

class ArchiveMenu(val game: PocketPoker) : BaseScreen {

    private val stage = Stage(game.view)
    private val PADDING = 50f
    private val archiveList: ScrollableContainer

    init {

        archiveList = ScrollableContainer(object: ClickHandler() {
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

        table.add(TextField("", editStyle)).pad(PADDING).expand().fill().row()
        table.add(archiveList.actor).row()
        val mainMenuButton = TextButton(Settings.getText(Settings.TextKeys.MAIN_MENU), buttonStyle)
        mainMenuButton.addListener(game.switches[ScreenType.MAIN_MENU])
        table.add(mainMenuButton).pad(PADDING).padRight(game.gameWidth * 0.3f).fill().height(100f)
        stage.addActor(table)

        //test data
        archiveList.add(ArchiveItem(0, "Tournament #55", 10, 100, 20))
        archiveList.add(ArchiveItem(1, "Tournament #1253", 2, 10000, 43))
        archiveList.add(ArchiveItem(2, "Tournament #5622", 10, 500, 232))
        archiveList.add(ArchiveItem(3, "Tournament #55", 10, 100, 20))
        archiveList.add(ArchiveItem(4, "Tournament #1253", 2, 10000, 43))
        archiveList.add(ArchiveItem(5, "Tournament #5622", 10, 500, 232))
        archiveList.add(ArchiveItem(6, "Tournament #55", 10, 100, 20))
        archiveList.add(ArchiveItem(7, "Tournament #1253", 2, 10000, 43))
        archiveList.add(ArchiveItem(8, "Tournament #5622", 10, 500, 232))

    }

    override fun update(){

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

    override fun recieveFromServer(json: JsonObject) {

    }
}
