package game.poker.gui

import com.badlogic.gdx.graphics.g2d.Sprite
import game.poker.staticFiles.Textures
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import game.poker.staticFiles.Fonts
import game.poker.Settings


class Button(private val x: Float, private val y: Float,
             private val width: Float, private val height: Float,
             private val key: Settings.TextKeys){
    private val background = Sprite(Textures.menuButton)
    private val font = Fonts.mainMenuButtonFont

    init{
        background.setBounds(x, y, width, height)
    }

    fun draw(batch: SpriteBatch){
        background.draw(batch)
        font.draw(batch, Settings.getText(key), x+width/4, (y + height/1.5).toFloat())
    }
}
