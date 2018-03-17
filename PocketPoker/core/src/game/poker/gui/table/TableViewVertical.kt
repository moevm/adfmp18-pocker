package game.poker.gui.table

import game.poker.PocketPoker

class TableViewVertical(game: PocketPoker) : TableViewBase(game) {
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
        chatButton.setPosition(850f, 300f)
        chatButton.setSize(200f, 90f)
        infoButton.setPosition(850f, 150f)
        infoButton.setSize(200f, 90f)
        foldButton.setPosition(20f, 20f)
        foldButton.setSize(250f, 90f)
        callButton.setPosition(370f, 20f)
        callButton.setSize(350f, 90f)
        raiseButton.setPosition(800f, 20f)
        raiseButton.setSize(250f, 90f)
    }
}
