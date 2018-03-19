package game.poker.gui.table

import game.poker.PocketPoker
import game.poker.screens.TableScreen

class TableViewVertical(game: PocketPoker, table: TableScreen) : TableViewBase(game, table) {
    init {
        for (i in 0..8){
            seats.add(SeatVertical(i))
        }
        seats.forEach { stage.addActor(it) }
        setUpCards()
        setUpButtons()
        pot.setPosition(450f,850f)
        pot.label.setPosition(-5f,450f)
    }
    private fun setUpCards() {
        cards.forEach { it.setSize(100f, 140f) }
        cards[0].setPosition(400f, 1100f)
        cards[1].setPosition(510f, 1100f)
        cards[2].setPosition(620f, 1100f)
        cards[3].setPosition(450f, 950f)
        cards[4].setPosition(560f, 950f)
    }
    private fun setUpButtons(){
        exitButton.setPosition(10f, 1790f)
        chatButton.setPosition(960f, 290f)
        chatButton.setSize(100f, 100f)
        infoButton.setPosition(960f, 170f)
        infoButton.setSize(100f, 100f)
        foldButton.setPosition(20f, 20f)
        foldButton.setSize(250f, 90f)
        callButton.setPosition(370f, 20f)
        callButton.setSize(350f, 90f)
        raiseButton.setPosition(800f, 20f)
        raiseButton.setSize(250f, 90f)
        nextHandButton.setPosition(680f, 20f)
        nextStepButton.setPosition(560f, 20f)
        prevHandButton.setPosition(320f, 20f)
        pausePlayButton.setPosition(440f, 20f)
        nextHandButton.setSize(100f, 100f)
        nextStepButton.setSize(100f, 100f)
        prevHandButton.setSize(100f, 100f)
        pausePlayButton.setSize(100f, 100f)
    }
}
