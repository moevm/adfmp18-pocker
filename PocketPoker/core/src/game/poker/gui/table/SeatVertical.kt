package game.poker.gui.table

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class SeatVertical(val positionNumber:Int) : SeatBase(){
    override fun updateCardsPosition(isCardsUp: Boolean){
        if (isCardsUp){
            card1.setPosition(0f,0f)
            card2.setPosition(20f, -10f)
        } else {
            card1.y = 0f
            card2.y = 0f
            when(positionNumber){
                0 -> {
                    card1.x = 0f
                    card2.x = 160f
                }
                else -> {
                    card1.x = 0f
                    card2.x = 105f
                }
            }
        }
    }

    init {
        when(positionNumber){
            0 -> {
                setPosition(450f, 150f)
                chipstack.setPosition(50f, 270f)
                dealerChip.setPosition(-10f,270f)
                playerView.setPosition(-350f, 0f)
            }
            1 -> {
                setPosition(20f, 500f)
                chipstack.setPosition(270f, 60f)
                dealerChip.setPosition(210f,60f)
                playerView.setPosition(0f, 150f)
            }
            2 -> {
                setPosition(20f, 950f)
                chipstack.setPosition(110f, -90f)
                dealerChip.setPosition(50f,-90f)
                playerView.setPosition(0f, 150f)
            }
            3 -> {
                setPosition(20f, 1400f)
                chipstack.setPosition(110f, -90f)
                dealerChip.setPosition(50f,-90f)
                playerView.setPosition(0f, 150f)
            }
            4 -> {
                setPosition(330f, 1600f)
                chipstack.setPosition(0f, -130f)
                dealerChip.setPosition(75f,-70f)
                playerView.setPosition(0f, 150f)
            }
            5 -> {
                setPosition(600f, 1600f)
                chipstack.setPosition(0f, -130f)
                dealerChip.setPosition(75f,-70f)
                playerView.setPosition(0f, 150f)
            }
            6 -> {
                setPosition(850f, 1400f)
                chipstack.setPosition(-150f, -90f)
                dealerChip.setPosition(60f,-90f)
                playerView.setPosition(0f, 150f)
            }
            7 -> {
                setPosition(850f, 950f)
                chipstack.setPosition(-150f, -90f)
                dealerChip.setPosition(60f,-90f)
                playerView.setPosition(0f, 150f)
            }
            8 -> {
                setPosition(850f, 500f)
                chipstack.setPosition(-260f, 60f)
                dealerChip.setPosition(-50f,60f)
                playerView.setPosition(0f, 150f)
            }
            else -> throw IllegalArgumentException("Bad index")
        }
        if (positionNumber == 0){
            card1.setSize(150f, 210f)
            card2.setSize(150f, 210f)
        } else {
            card1.setSize(100f, 140f)
            card2.setSize(100f, 140f)
        }
        card1.setPosition(0f,0f)
        card2.setPosition(0f,0f)
        updateCardsPosition(true)
        playerView.money = positionNumber.toString()

        chipstack.setChips(99999)
    }
}
