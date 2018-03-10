package game.poker.gui.table

import game.poker.PocketPoker

class TableViewHorizontal(game: PocketPoker) : TableViewBase(game) {
    init {
        for (i in 0..8){
            seats.add(SeatHorizontal(i))
        }
        seats.forEach { stage.addActor(it) }
        setUpCards()
        setUpButtons()
        pot.rotation = -90f
        pot.setPosition(400f,1090f)
        pot.label.setPosition(0f,250f)
    }
    private fun setUpCards() {
        cards.forEach {
            it.setSize(100f, 140f)
            it.rotation = -90f
        }
        cards[0].setPosition(500f, 1240f)
        cards[1].setPosition(500f, 1130f)
        cards[2].setPosition(500f, 1020f)
        cards[3].setPosition(500f, 910f)
        cards[4].setPosition(500f, 800f)
    }
    private fun setUpButtons(){
        exitButton.setPosition(940f, 1790f)
        chatButton.setPosition(200f, 300f)
        chatButton.setSize(200f, 90f)
        infoButton.setPosition(100f, 300f)
        infoButton.setSize(200f, 90f)
        foldButton.setPosition(20f, 1700f)
        foldButton.setSize(250f, 90f)
        callButton.setPosition(20f, 1000f)
        callButton.setSize(350f, 90f)
        raiseButton.setPosition(20f, 580f)
        raiseButton.setSize(250f, 90f)
        exitButton.rotation = -90f
        chatButton.rotation = -90f
        infoButton.rotation = -90f
        foldButton.rotation = -90f
        callButton.rotation = -90f
        raiseButton.rotation = -90f
    }
}
