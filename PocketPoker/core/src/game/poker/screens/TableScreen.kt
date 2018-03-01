package game.poker.screens

import game.poker.PocketPoker
import game.poker.gui.Seat

class TableScreen(val game: PocketPoker) : BaseScreen {

    var isLandscape = false
        private set
    // if isLandscape true then render horizontal layout else vertical layout

    var isFinal = false
    // describes is this table is final or not
    // note: in final table all empty seats are hidden
    //     but in other tables all empty seats just shows with text "Empty seat"

    private val seats = Array(9) { Seat(this) }

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