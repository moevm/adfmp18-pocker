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
import game.poker.gui.TournamentItem

class TournamentMenu(val game: PocketPoker) : BaseScreen {

    private val stage = Stage(game.view)
    private val PADDING = 50f
    private val tournamentsList: ScrollableContainer

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
        val createButton = TextButton(Settings.getText(Settings.TextKeys.CREATE_TOURNAMENT), buttonStyle)
        //createButton.addListener(game.switches[ScreenType.CREATE_TOURNAMENT]) TODO: go to creating menu
        table.add(createButton).pad(PADDING).fill().height(100f).row()
        table.add(TextField("", editStyle)).pad(PADDING).expand().fill().row()
        table.add(tournamentsList.actor).row()
        val mainMenuButton = TextButton(Settings.getText(Settings.TextKeys.MAIN_MENU), buttonStyle)
        mainMenuButton.addListener(game.switches[ScreenType.MAIN_MENU])
        table.add(mainMenuButton).pad(PADDING).padRight(game.gameWidth * 0.3f).fill().height(100f)
        stage.addActor(table)

        //test data
        tournamentsList.add(TournamentItem(0, "Tournament #0", 20, 100,
                10, 20, 5, false,true))
        tournamentsList.add(TournamentItem(1, "Tournament #1", 30, 1000,
                10, 20, 5, true))
        tournamentsList.add(TournamentItem(2, "Tournament #2", 11, 1000,
                10, 20, 5, false,true))
        tournamentsList.add(TournamentItem(3, "Tournament #3", 5, 5000,
                10, 20, 5, true))
        val item = TournamentItem(4, "Tournament #4", 3, 2000,
                10, 20, 5, false)
        tournamentsList.add(item)
        item.players = 2
        item.isStarted = true
        item.update()



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
