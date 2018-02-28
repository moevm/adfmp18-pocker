package game.poker.gui

import game.poker.core.Card
import game.poker.staticFiles.Textures


class CardTexture {
    var xPos: Int = 0
    var yPos: Int = 0
    var texture = Textures.cardPlaceholder
        private set

    fun setCard(card: Card){
        texture = Textures.getCard(card)
    }

    fun clearCard(){
        texture = Textures.cardPlaceholder
    }
}