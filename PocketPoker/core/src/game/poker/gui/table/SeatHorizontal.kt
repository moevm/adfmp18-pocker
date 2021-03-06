package game.poker.gui.table

import game.poker.gui.table.TableViewBase.Point

class SeatHorizontal(private val positionNumber:Int, private val potPosition: Point) : SeatBase(){
    override fun updateCardsPosition(){
        if (isCardsUp){
            card1.setPosition(70f,0f)
            card2.setPosition(90f, -10f)
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
                setPosition(120f, 1100f)
                chipstackPosition.setLocation(350f, 150f)
                dealerChip.setPosition(400f, 30f)
                playerView.setPosition(-270f, 0f)
            }
            1 -> {
                setPosition(140f, 1800f)
                chipstackPosition.setLocation(250f, 200f)
                dealerChip.setPosition(180f,200f)
                playerView.setPosition(-100f, 150f)
            }
            2 -> {
                setPosition(460f, 1900f)
                chipstackPosition.setLocation(300f, 60f)
                dealerChip.setPosition(230f,60f)
                playerView.setPosition(0f, 150f)
            }
            3 -> {
                setPosition(780f, 1640f)
                chipstackPosition.setLocation(100f, -90f)
                dealerChip.setPosition(30f,-140f)
                playerView.setPosition(-260f,0f)
            }
            4 -> {
                setPosition(900f, 1430f)
                myPotPosition.setLocation(340f, -500f)
                chipstackPosition.setLocation(50f, -80f)
                dealerChip.setPosition(270f,-100f)
                playerView.setPosition(210f, 0f)
            }
            5 -> {
                setPosition(900f, 970f)
                chipstackPosition.setLocation(50f, -80f)
                dealerChip.setPosition(270f,-100f)
                playerView.setPosition(210f, 0f)
            }
            6 -> {
                setPosition(800f, 500f)
                chipstackPosition.setLocation(-100f, -90f)
                dealerChip.setPosition(100f,-90f)
                playerView.setPosition(210f, 0f)
            }
            7 -> {
                setPosition(460f, 250f)
                chipstackPosition.setLocation(-260f, 60f)
                dealerChip.setPosition(-60f,60f)
                playerView.setPosition(-40f, 150f)
            }
            8 -> {
                setPosition(140f, 400f)
                chipstackPosition.setLocation(-200f, 250f)
                dealerChip.setPosition(30f,200f)
                playerView.setPosition(100f, 150f)
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
        myPotPosition.setLocation(y - potPosition.y, potPosition.x - x)
        chipstack.setPosition(chipstackPosition.x, chipstackPosition.y)
        card1.setPosition(0f,0f)
        card2.setPosition(0f,0f)
        updateCardsPosition()
        playerView.money = positionNumber.toString()
        rotation = -90f
    }
}
