package game.poker.gui.table

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import game.poker.Settings
import game.poker.core.Card
import game.poker.core.Chip
import game.poker.core.Rank
import game.poker.core.Suit
import game.poker.core.Visibility
import game.poker.staticFiles.Textures


abstract class SeatBase : Group() {
    var isEmpty = false
        set(value) {
            field = value
            if (value) {
                playerView.setText(Settings.getText(Settings.TextKeys.EMPTY_SEAT))
                clearCards()
                setChips(0)
            }
        }
    var isDealer = false
        set(value) {
            field = value
            dealerChip.isVisible = value
        }
    var isCardsUp = false
        private set
    var isCardsEmpty = true
    private var moveCount = 0

    protected val chipstack = Chipstack()
    val playerView = PlayerView()
    protected var card1 = Image(SpriteDrawable(Sprite(Textures.cardBackground)))
    protected var card2 = Image(SpriteDrawable(Sprite(Textures.cardBackground)))
    protected val dealerChip = Image(SpriteDrawable(Sprite(Textures.getChip(Chip.DEALER))))
    var cardName1 = Card(Rank.Ace, Suit.Spades, Visibility.Open)
    var cardName2 = Card(Rank.Ace, Suit.Spades, Visibility.Open)

    fun checkUpdates(){
        if(needUpdateCards){
            updateCards()
        }
        if(playerView.needUpdateBackground){
            playerView.updateBackground()
        }
        if(chipstack.needUpdateChips){
            chipstack.updateChips()
        }
    }

    fun fit(other: SeatBase){
        playerView.money = other.playerView.money
        playerView.playerName = other.playerView.playerName
        playerView.info = other.playerView.info
        setChips(other.getChips())
        isDealer = other.isDealer
        isEmpty = other.isEmpty
        if (other.isCardsUp) {
            upCards()
        } else {
            setCards(other.cardName1, other.cardName2)
        }
        if (other.isCardsEmpty) {
            clearCards()
        }
        playerView.isDisabled = other.playerView.isDisabled
        playerView.isActive = other.playerView.isActive
        isVisible = other.isVisible
    }

    var needUpdateCards = false
        private set

    fun setCards(newCard1: Card, newCard2: Card){
        cardName1 = newCard1
        cardName2 = newCard2
        needUpdateCards = true
    }

    fun updateCards(){
        card1.drawable = SpriteDrawable(Sprite(Textures.getCard(cardName1)))
        card2.drawable = SpriteDrawable(Sprite(Textures.getCard(cardName2)))
        isCardsUp = false
        isCardsEmpty = false
        updateCardsPosition()
        needUpdateCards = false
    }

    fun upCards(){
        card1.drawable = SpriteDrawable(Sprite(Textures.cardBackground))
        card2.drawable = SpriteDrawable(Sprite(Textures.cardBackground))
        isCardsUp = true
        isCardsEmpty = false
        updateCardsPosition()
    }

    fun clearCards(){
        card1.drawable = SpriteDrawable(Sprite(Textures.cardPlaceholder))
        card2.drawable = SpriteDrawable(Sprite(Textures.cardPlaceholder))
        isCardsEmpty = true
    }

    fun setChips(chips: Long) {
        chipstack.setChips(chips)
    }

    fun getChips(): Long {
        return chipstack.money
    }

    protected abstract fun updateCardsPosition()

    init {
        dealerChip.setSize(50f, 50f)
        dealerChip.isVisible = false
        addActor(card1)
        addActor(card2)
        addActor(playerView)
        addActor(chipstack)
        addActor(dealerChip)
    }

    fun update() {
        if (isEmpty) {
            playerView.playerName = Settings.getText(Settings.TextKeys.EMPTY_SEAT)
        }
    }

    protected abstract fun moveChips(step: Float)

    fun moveChipsToPot(step: Float){
        moveChips(step)
    }
}
