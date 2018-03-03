package game.poker.gui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import game.poker.core.Card
import game.poker.staticFiles.Textures


class Seat(val positionNumber:Int) : Widget(){
    // if playerView is null in final table should hide
    // but not in final table should be visible with text "Empty seat"
    var chipstack = Chipstack()
    var playerView : PlayerView
    private var card1 = Image(SpriteDrawable(Sprite(Textures.cardBackground)))
    private var card2 = Image(SpriteDrawable(Sprite(Textures.cardBackground)))

    fun setCards(newCard1: Card,newCard2: Card){
        card1.drawable = SpriteDrawable(Sprite(Textures.getCard(newCard1)))
        card2.drawable = SpriteDrawable(Sprite(Textures.getCard(newCard2)))
        updateCardsPosition(false)
    }

    fun overturnCards(){
        card1.drawable = SpriteDrawable(Sprite(Textures.cardBackground))
        card2.drawable = SpriteDrawable(Sprite(Textures.cardBackground))
        updateCardsPosition(true)
    }

    fun clearCards(){
        card1.drawable = SpriteDrawable(Sprite(Textures.cardPlaceholder))
        card2.drawable = SpriteDrawable(Sprite(Textures.cardPlaceholder))
    }

    private fun updateCardsPosition(isCardsUp: Boolean){
        if (isCardsUp){
            card1.x = x
            card1.y = y
            card2.x = x + 20f
            card2.y = y - 20f
        } else {
            card1.y = y
            card2.y = y
            if(positionNumber == 0){
                card1.x = x - 80f
                card2.x = x + 80f
                return
            }
            if (positionNumber == 6 || positionNumber == 7 || positionNumber == 8){
                card1.x = x - 50f
                card2.x = x + 50f
            }
            else {
                card2.x = x + 110f
            }
        }
    }

    init {
        when(positionNumber){
            0 -> {
                x = 500f
                y = 100f
            }
            1 -> {
                x = 70f
                y = 500f
            }
            2 -> {
                x = 70f
                y = 950f
            }
            3 -> {
                x = 70f
                y = 1400f
            }
            4 -> {
                x = 330f
                y = 1600f
            }
            5 -> {
                x = 650f
                y = 1600f
            }
            6 -> {
                x = 900f
                y = 1400f
            }
            7 -> {
                x = 900f
                y = 950f
            }
            8 -> {
                x = 900f
                y = 500f
            }
            else -> throw IllegalArgumentException("Bad index")
        }
        if (positionNumber == 0){
            card1.setSize(150f,210f)
            card2.setSize(150f,210f)
        } else {
            card1.setSize(100f,140f)
            card2.setSize(100f,140f)
        }
        card1.setPosition(x,y)
        card2.setPosition(x,y)
        updateCardsPosition(true)
        playerView = PlayerView(this)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        card1.draw(batch, parentAlpha)
        card2.draw(batch, parentAlpha)
        playerView.draw(batch, parentAlpha)
    }
}
