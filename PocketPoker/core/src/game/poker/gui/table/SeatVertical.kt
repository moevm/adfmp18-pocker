package game.poker.gui.table


class SeatVertical(private val positionNumber:Int, private val table: TableViewBase) : SeatBase(){
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
                chipstackPosition.setLocation(50, 270)
                dealerChip.setPosition(-10f,270f)
                playerView.setPosition(-350f, 0f)
            }
            1 -> {
                setPosition(20f, 500f)
                chipstackPosition.setLocation(270, 60)
                dealerChip.setPosition(210f,60f)
                playerView.setPosition(0f, 150f)
            }
            2 -> {
                setPosition(20f, 950f)
                chipstackPosition.setLocation(110, -90)
                dealerChip.setPosition(50f,-90f)
                playerView.setPosition(0f, 150f)
            }
            3 -> {
                setPosition(20f, 1400f)
                chipstackPosition.setLocation(110, -90)
                dealerChip.setPosition(50f,-90f)
                playerView.setPosition(0f, 150f)
            }
            4 -> {
                setPosition(330f, 1600f)
                chipstackPosition.setLocation(0, -130)
                dealerChip.setPosition(75f,-70f)
                playerView.setPosition(0f, 150f)
            }
            5 -> {
                setPosition(600f, 1600f)
                chipstackPosition.setLocation(0, -130)
                dealerChip.setPosition(75f,-70f)
                playerView.setPosition(0f, 150f)
            }
            6 -> {
                setPosition(850f, 1400f)
                chipstackPosition.setLocation(-150, -90)
                dealerChip.setPosition(60f,-90f)
                playerView.setPosition(-40f, 150f)
            }
            7 -> {
                setPosition(850f, 950f)
                chipstackPosition.setLocation(-150, -90)
                dealerChip.setPosition(60f,-90f)
                playerView.setPosition(-40f, 150f)
            }
            8 -> {
                setPosition(850f, 500f)
                chipstackPosition.setLocation(-260, 60)
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
        val potPos = table.getPotPosition()
        myPotPosition.setLocation(potPos.x - x.toInt(), potPos.y - y.toInt())
        chipstack.setPosition(chipstackPosition.x.toFloat(), chipstackPosition.y.toFloat())
        card1.setPosition(0f,0f)
        card2.setPosition(0f,0f)
        updateCardsPosition()
        playerView.money = positionNumber.toString()
    }
}
