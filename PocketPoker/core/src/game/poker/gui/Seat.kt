package game.poker.gui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import game.poker.Settings
import game.poker.core.Card
import game.poker.staticFiles.Textures


class Seat(val positionNumber:Int) : Widget(){
    // if playerView is null in final table should hide
    // but not in final table should be visible with text "Empty seat"
    var isEmpty = false
    private val chipstack = Chipstack(x,y)
    var playerView : PlayerView //todo: make private
    private var card1 = Image(SpriteDrawable(Sprite(Textures.cardBackground)))
    private var card2 = Image(SpriteDrawable(Sprite(Textures.cardBackground)))

    fun setCards(newCard1: Card,newCard2: Card){
        card1.drawable = SpriteDrawable(Sprite(Textures.getCard(newCard1)))
        card2.drawable = SpriteDrawable(Sprite(Textures.getCard(newCard2)))
        updateCardsPosition(false)
    }

    fun upCards(){
        card1.drawable = SpriteDrawable(Sprite(Textures.cardBackground))
        card2.drawable = SpriteDrawable(Sprite(Textures.cardBackground))
        updateCardsPosition(true)
    }

    fun clearCards(){
        card1.drawable = SpriteDrawable(Sprite(Textures.cardPlaceholder))
        card2.drawable = SpriteDrawable(Sprite(Textures.cardPlaceholder))
    }

    fun setChips(chips: Long) {
        chipstack.setChips(chips)
    }

    private fun updateCardsPosition(isCardsUp: Boolean){
        if (isCardsUp){
            card1.setPosition(x,y)
            card2.setPosition(x + 20f, y - 20f)
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
                setPosition(500f, 150f)
                chipstack.setPosition(x - 50f, y + 300f)
            }
            1 -> {
                setPosition(70f, 500f)
                chipstack.setPosition(x + 210f, y + 60f)
            }
            2 -> {
                setPosition(70f, 950f)
                chipstack.setPosition(x + 80f, y - 90f)
            }
            3 -> {
                setPosition(70f, 1400f)
                chipstack.setPosition(x + 80f, y - 90f)
            }
            4 -> {
                setPosition(330f, 1600f)
                chipstack.setPosition(x, y - 100f)
            }
            5 -> {
                setPosition(600f, 1600f)
                chipstack.setPosition(x, y - 100f)
            }
            6 -> {
                setPosition(900f, 1400f)
                chipstack.setPosition(x - 150f, y - 90f)
            }
            7 -> {
                setPosition(900f, 950f)
                chipstack.setPosition(x - 150f, y - 90f)
            }
            8 -> {
                setPosition(900f, 500f)
                chipstack.setPosition(x - 260f, y + 60f)
            }
            else -> throw IllegalArgumentException("Bad index")
        }
        if (positionNumber == 0){
            card1.setSize(150f, 210f)
            card2.setSize(150f, 210f)
        } else {
            card1.setSize(100f, 140f)
            card2.setSize(100f, 140f)
        }
        card1.setPosition(x,y)
        card2.setPosition(x,y)
        updateCardsPosition(true)
        playerView = PlayerView(this)
        chipstack.setChips(9999)
    }

    fun update(){
        if (isEmpty) {
            playerView.playerName = Settings.getText(Settings.TextKeys.EMPTY_PLAYER) + "" //String? -> String
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        card1.draw(batch, parentAlpha)
        card2.draw(batch, parentAlpha)
        playerView.draw(batch, parentAlpha)
        chipstack.draw(batch, parentAlpha)
    }
}
