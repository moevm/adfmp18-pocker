package game.poker.gui.table


class SeatVertical(private val positionNumber:Int) : SeatBase(){
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

    override fun moveChips(step: Float) {
        //if (step == 1) (x + chipstack.x) = 550
        //if (step == 1) (y + chipstack.y) = 850
        when(positionNumber){
            0 -> {
                chipstack.setPosition(50f + 50f * step, 270f + 430f * step)
            }
            1 -> {
                chipstack.setPosition(270f + 260f * step, 60f + 290f * step)
            }
            2 -> {
                chipstack.setPosition(110f + 420f * step, -90f - 10f * step)
            }
            3 -> {
                chipstack.setPosition(110f + 420f * step, -90f - 460f * step)
            }
            4 -> {
                chipstack.setPosition(0f + 220f * step, -130f - 620f * step)
            }
            5 -> {
                chipstack.setPosition(0f + -50f * step, -130f - 620f * step)
            }
            6 -> {
                chipstack.setPosition(-150f + -150f * step, -90f - 460f * step)
            }
            7 -> {
                chipstack.setPosition(-150f + -150f * step, -90f - 10f * step)
            }
            8 -> {
                chipstack.setPosition(-260f + -40f * step, 60f + 290f * step)
            }
            else -> throw IllegalArgumentException("Bad index")
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
                playerView.setPosition(-40f, 150f)
            }
            7 -> {
                setPosition(850f, 950f)
                chipstack.setPosition(-150f, -90f)
                dealerChip.setPosition(60f,-90f)
                playerView.setPosition(-40f, 150f)
            }
            8 -> {
                setPosition(850f, 500f)
                chipstack.setPosition(-260f, 60f)
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
        card1.setPosition(0f,0f)
        card2.setPosition(0f,0f)
        updateCardsPosition()
        playerView.money = positionNumber.toString()

        chipstack.setChips(99999)
    }
}
