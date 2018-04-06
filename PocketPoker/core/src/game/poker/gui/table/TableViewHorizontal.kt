package game.poker.gui.table

import com.google.gson.JsonObject
import game.poker.PocketPoker
import game.poker.screens.TableScreen

class TableViewHorizontal(game: PocketPoker, table: TableScreen) : TableViewBase(game, table) {
    init {
        setUpCards()
        setUpButtons()
        pot.rotation = -90f
        pot.setPosition(400f,1090f)
        pot.label.setPosition(0f,250f)
        for (i in 0..8){
            seats.add(SeatHorizontal(i, getPotPosition()))
        }
        seats.forEach { stage.addActor(it) }
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
        exitButton.setPosition(940f, 1890f)
        chatButton.setPosition(20f, 160f)
        chatButton.setSize(200f, 90f)
        infoButton.setPosition(20f, 280f)
        infoButton.setSize(200f, 90f)
        foldButton.setPosition(20f, 1700f)
        foldButton.setSize(250f, 90f)
        callButton.setPosition(20f, 1300f)
        callButton.setSize(350f, 90f)
        raiseButton.setPosition(20f, 880f)
        raiseButton.setSize(250f, 90f)
        nextHandButton.setPosition(20f, 820f)
        nextStepButton.setPosition(20f, 940f)
        prevHandButton.setPosition(20f, 1180f)
        pausePlayButton.setPosition(20f, 1060f)
        nextHandButton.setSize(100f, 100f)
        nextStepButton.setSize(100f, 100f)
        prevHandButton.setSize(100f, 100f)
        pausePlayButton.setSize(100f, 100f)
        exitButton.rotation = -90f
        chatButton.rotation = -90f
        infoButton.rotation = -90f
        foldButton.rotation = -90f
        callButton.rotation = -90f
        raiseButton.rotation = -90f
        nextHandButton.rotation = -90f
        nextStepButton.rotation = -90f
        prevHandButton.rotation = -90f
        pausePlayButton.rotation = -90f
    }

    override fun receiveFromServer(json: JsonObject) {

    }
}
