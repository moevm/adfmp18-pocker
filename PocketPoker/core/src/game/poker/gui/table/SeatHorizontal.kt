package game.poker.gui.table

class SeatHorizontal(private val positionNumber:Int) : SeatBase(){
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
                myPotPosition.setLocation(10, 280)
                chipstackPosition.setLocation(350, 150)
                dealerChip.setPosition(400f, 30f)
                playerView.setPosition(-270f, 0f)
            }
            1 -> {
                setPosition(140f, 1800f)
                myPotPosition.setLocation(710, 260)
                chipstackPosition.setLocation(250, 200)
                dealerChip.setPosition(180f,200f)
                playerView.setPosition(-100f, 150f)
            }
            2 -> {
                setPosition(460f, 1900f)
                myPotPosition.setLocation(810, -60)
                chipstackPosition.setLocation(300, 60)
                dealerChip.setPosition(230f,60f)
                playerView.setPosition(0f, 150f)
            }
            3 -> {
                setPosition(780f, 1640f)
                myPotPosition.setLocation(550, -380)
                chipstackPosition.setLocation(100, -90)
                dealerChip.setPosition(30f,-140f)
                playerView.setPosition(-260f,0f)
            }
            4 -> {
                setPosition(900f, 1430f)
                myPotPosition.setLocation(340, -500)
                chipstackPosition.setLocation(50, -80)
                dealerChip.setPosition(270f,-100f)
                playerView.setPosition(210f, 0f)
            }
            5 -> {
                setPosition(900f, 970f)
                myPotPosition.setLocation(-120, -500)
                chipstackPosition.setLocation(50, -80)
                dealerChip.setPosition(270f,-100f)
                playerView.setPosition(210f, 0f)
            }
            6 -> {
                setPosition(800f, 500f)
                myPotPosition.setLocation(-590, -400)
                chipstackPosition.setLocation(-100, -90)
                dealerChip.setPosition(100f,-90f)
                playerView.setPosition(210f, 0f)
            }
            7 -> {
                setPosition(460f, 250f)
                myPotPosition.setLocation(-840, -60)
                chipstackPosition.setLocation(-260, 60)
                dealerChip.setPosition(-60f,60f)
                playerView.setPosition(-40f, 150f)
            }
            8 -> {
                setPosition(140f, 400f)
                myPotPosition.setLocation(-690, 260)
                chipstackPosition.setLocation(-200, 250)
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
        card1.setPosition(0f,0f)
        card2.setPosition(0f,0f)
        chipstack.setPosition(chipstackPosition.x.toFloat(), chipstackPosition.y.toFloat())
        updateCardsPosition()
        playerView.money = positionNumber.toString()
        rotation = -90f
    }
}
