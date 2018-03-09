package game.poker.gui.table

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import game.poker.Settings
import game.poker.core.Card
import game.poker.staticFiles.Textures


abstract class SeatBase : Group() {
    // if playerView is null in final table should hide
    // but not in final table should be visible with text "Empty seat"
    var isEmpty = false
    protected val chipstack = Chipstack()
    var playerView = PlayerView() //todo: make private
    protected var card1 = Image(SpriteDrawable(Sprite(Textures.cardBackground)))
    protected var card2 = Image(SpriteDrawable(Sprite(Textures.cardBackground)))

    fun setCards(newCard1: Card, newCard2: Card){
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

    protected abstract fun updateCardsPosition(isCardsUp: Boolean)

    init {
        chipstack.setChips(99)
        addActor(card1)
        addActor(card2)
        addActor(playerView)
        addActor(chipstack)
    }

    fun update() {
        if (isEmpty) {
            playerView.playerName = Settings.getText(Settings.TextKeys.EMPTY_SEAT)
        }
    }
}
