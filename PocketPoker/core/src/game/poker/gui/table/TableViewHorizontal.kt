package game.poker.gui.table

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.google.gson.JsonObject
import game.poker.PocketPoker
import game.poker.screens.TableScreen

class TableViewHorizontal(game: PocketPoker, table: TableScreen) : TableViewBase(game, table) {
    init {
        setUpCards()
        setUpButtons()
        //создание диалога рейза
        val dialog = RaiseDialogHorizontal(stage, handler)
        rightChoiceButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                //вызов диалога рейза
                dialog.show(minRaise = raiseInfo.minRaise, maxRaise = raiseInfo.maxRaise,
                        raiseStep = raiseInfo.raiseStep, pot = raiseInfo.pot)
            }
        })
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
        leftChoiceButton.setPosition(20f, 1700f)
        leftChoiceButton.setSize(250f, 90f)
        cenralChoiceButton.setPosition(20f, 1300f)
        cenralChoiceButton.setSize(350f, 90f)
        rightChoiceButton.setPosition(20f, 880f)
        rightChoiceButton.setSize(250f, 90f)
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
        leftChoiceButton.rotation = -90f
        cenralChoiceButton.rotation = -90f
        rightChoiceButton.rotation = -90f
        nextHandButton.rotation = -90f
        nextStepButton.rotation = -90f
        prevHandButton.rotation = -90f
        pausePlayButton.rotation = -90f
    }

    override fun receiveFromServer(json: JsonObject) {

    }
}
