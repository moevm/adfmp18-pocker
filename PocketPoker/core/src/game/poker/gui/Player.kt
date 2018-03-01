package game.poker.gui


class Player(val seat: Seat, var name: String, var money: Int) {

    var isEmpty = false
    // if it is empty seat in final table should hide
    // but not in final table should be visible with text "Empty seat"

    var info: String = ""
    var isDisabled = false // gray background - player in game but disconnected
    var isActive = false // yellow background - player is thinking
}