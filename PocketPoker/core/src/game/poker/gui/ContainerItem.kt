package game.poker.gui

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
import game.poker.gui.ScrollableContainer.ClickHandler

open class ContainerItem(val id: Int): Table() {

    protected val PADDING = 10f
    protected val labelStyle = Label.LabelStyle(Fonts.containerItemFont, Color.BLACK)
    protected val nextSprite = SpriteDrawable(Sprite(Textures.next))
    protected val watchSprite = SpriteDrawable(Sprite(Textures.watch))
    var clickHandler = ClickHandler()
    var clickListener: ClickListener

    init {
        pad(PADDING)
        align(Align.center)
        background = SpriteDrawable(Sprite(Textures.menuButton))

        clickListener = object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                clickHandler.click(id)
            }
        }
    }

    open fun update() {

    }

}