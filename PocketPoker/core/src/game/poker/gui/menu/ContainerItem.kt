package game.poker.gui.menu

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.utils.Align
import game.poker.staticFiles.Fonts
import game.poker.staticFiles.Textures
import game.poker.gui.menu.ScrollableContainer.ClickHandler

open class ContainerItem(val id: Int): Table() {

    protected val PADDING = 10f
    protected val ICON_WIDTH = 90f
    protected val ICON_HEIGHT = 70f
    protected val labelStyle = Label.LabelStyle(Fonts.containerItemFont, Color.BLACK)
    protected val nextSprite = SpriteDrawable(Sprite(Textures.next))
    protected val watchSprite = SpriteDrawable(Sprite(Textures.watch))
    protected val playersSprite = SpriteDrawable(Sprite(Textures.players))
    protected val tablesSprite = SpriteDrawable(Sprite(Textures.tables))
    protected val handsSprite = SpriteDrawable(Sprite(Textures.hands))
    protected val lockedSprite = SpriteDrawable(Sprite(Textures.locked))
    protected val unlockedSprite = SpriteDrawable(Sprite(Textures.unlocked))
    protected val blindsSprite = SpriteDrawable(Sprite(Textures.blinds))
    var clickHandler = ClickHandler()
    var clickListener: ClickListener
    var watchClickListener: ClickListener
    var nextClickListener: ClickListener

    init {
        pad(PADDING * 2f)
        align(Align.center)
        background = SpriteDrawable(Sprite(Textures.menuButton))

        clickListener = object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                clickHandler.click(id, true)
            }
        }

        watchClickListener = object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                clickHandler.click(id, false)
            }
        }

        nextClickListener = object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                clickHandler.click(id, true)
            }
        }
    }

}