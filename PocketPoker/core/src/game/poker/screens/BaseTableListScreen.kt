package game.poker.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.google.gson.JsonArray

import game.poker.PocketPoker
import game.poker.staticFiles.Textures
import game.poker.staticFiles.Fonts
import game.poker.gui.menu.ScrollableContainer
import game.poker.gui.menu.ScrollableContainer.ClickHandler


abstract class BaseTableListScreen(val game: PocketPoker) : BaseScreen {

    private val stage = Stage(game.view)
    private val PADDING = 50f
    protected val tableList: ScrollableContainer
    protected var tablesData = JsonArray()
    protected val backButton: TextButton
    protected val searchEdit: TextField
    protected var needUpdate = false

    init {

        val clickHandler = object: ClickHandler() {
            override fun click(itemId: Int, mode: Boolean) {
                handleItemClick(itemId)
            }
        }
        tableList = ScrollableContainer()
        tableList.clickHandler = clickHandler

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

        searchEdit = TextField("", editStyle)
        searchEdit.setTextFieldListener { textField, key -> updateList()}
        table.add(searchEdit).pad(PADDING).expandX().fillX().row()
        table.add(tableList.actor).expandX().fillX().row()
        backButton = TextButton("", buttonStyle)
        table.add(backButton).pad(PADDING).expand().left().bottom()
        stage.addActor(table)

    }

    override fun show(){
        Gdx.input.inputProcessor = stage
        sendRequestForUpdateList()
    }

    override fun render(delta: Float) {
        stage.act()
        stage.draw()
        if (needUpdate) {
            updateList()
            needUpdate = false
        }
    }

    override fun resize(width: Int, height: Int){

    }

    override fun pause(){

    }

    override fun resume(){

    }

    override fun hide() {
        tableList.clear()
    }

    override fun dispose(){

    }

    abstract fun updateList()

    abstract fun handleItemClick(id: Int)

    abstract fun sendRequestForUpdateList()

}
