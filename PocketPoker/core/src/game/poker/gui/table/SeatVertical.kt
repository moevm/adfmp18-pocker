package game.poker.gui.table

import game.poker.gui.table.TableViewBase.Point


class SeatVertical(private val positionNumber:Int, private val potPosition: Point) : SeatBase(){
    override fun updateCardsPosition(){
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
                chipstackPosition.setLocation(50f, 270f)
                dealerChip.setPosition(-10f,270f)
                playerView.setPosition(-350f, 0f)
            }
            1 -> {
                setPosition(20f, 500f)
                chipstackPosition.setLocation(270f, 60f)
                dealerChip.setPosition(210f,60f)
                playerView.setPosition(0f, 150f)
            }
            2 -> {
                setPosition(20f, 950f)
                chipstackPosition.setLocation(110f, -90f)
                dealerChip.setPosition(50f,-90f)
                playerView.setPosition(0f, 150f)
            }
            3 -> {
                setPosition(20f, 1400f)
                chipstackPosition.setLocation(110f, -90f)
                dealerChip.setPosition(50f,-90f)
                playerView.setPosition(0f, 150f)
            }
            4 -> {
                setPosition(330f, 1600f)
                chipstackPosition.setLocation(0f, -130f)
                dealerChip.setPosition(75f,-70f)
                playerView.setPosition(0f, 150f)
            }
            5 -> {
                setPosition(600f, 1600f)
                chipstackPosition.setLocation(0f, -130f)
                dealerChip.setPosition(75f,-70f)
                playerView.setPosition(0f, 150f)
            }
            6 -> {
                setPosition(850f, 1400f)
                chipstackPosition.setLocation(-150f, -90f)
                dealerChip.setPosition(60f,-90f)
                playerView.setPosition(-40f, 150f)
            }
            7 -> {
                setPosition(850f, 950f)
                chipstackPosition.setLocation(-150f, -90f)
                dealerChip.setPosition(60f,-90f)
                playerView.setPosition(-40f, 150f)
            }
            8 -> {
                setPosition(850f, 500f)
                chipstackPosition.setLocation(-260f, 60f)
                dealerChip.setPosition(-50f,60f)
                playerView.setPosition(-40f, 150f)
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
        myPotPosition.setLocation(potPosition.x - x, potPosition.y - y)
        chipstack.setPosition(chipstackPosition.x, chipstackPosition.y)
        card1.setPosition(0f,0f)
        card2.setPosition(0f,0f)
        updateCardsPosition()
        playerView.money = positionNumber.toString()
    }
}
