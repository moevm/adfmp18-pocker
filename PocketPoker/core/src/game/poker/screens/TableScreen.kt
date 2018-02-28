package game.poker.screens

import game.poker.PocketPoker
import game.poker.gui.Seat

class TableScreen(val game: PocketPoker) : BaseScreen {

    private var isLandscape: Boolean = false
    private var seats: Array<Seat>

    init {
        seats = Array(9) { Seat() }
    }

    override fun update(){

    }

    override fun show(){

    }

    override fun render(delta: Float) {

    }

    override fun resize(width: Int, height: Int){

    }

    override fun pause(){

    }

    override fun resume(){

    }

    override fun hide(){

    }

    override fun dispose(){

    }
}