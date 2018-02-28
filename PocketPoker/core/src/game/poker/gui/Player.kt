package game.poker.gui


class Player(val name: String, var money: Int) {
    var info: String = ""
    var isDisabled = false // gray background - player in game but disconnected
    var isActive = false // yellow background - player is thinking
}