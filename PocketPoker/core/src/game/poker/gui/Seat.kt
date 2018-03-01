package game.poker.gui

import game.poker.screens.TableScreen


class Seat(val table: TableScreen) {

    val card1 = CardTexture()
    val card2 = CardTexture()

    val chipstack = Chipstack()

    val player = Player(this,"", 0)

}