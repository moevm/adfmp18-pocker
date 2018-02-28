package game.poker.gui


class Seat {

    var isVisible = false
    // if player is null in final table should hide
    // but not in final table should be visible with text "Empty seat"

    var card1 = CardTexture()
    var card2 = CardTexture()

    var chipstack = Chipstack()

    var player: Player? = null

}