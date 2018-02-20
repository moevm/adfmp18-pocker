package game.poker.gui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import game.poker.Textures
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class Button(private val x: Float, private val y: Float,
             private val width: Float, private val height: Float,
             private val text: String){
    private val background = Sprite(Textures.menuButton)
    private val font = BitmapFont()

    init{
        background.setBounds(x, y, width, height)
        font.color = Color.BLACK
        font.data.setScale(5f)
    }

    fun draw(batch: SpriteBatch){
        background.draw(batch)
        font.draw(batch, text, x+width/4, (y + height/1.5).toFloat())
    }
}
