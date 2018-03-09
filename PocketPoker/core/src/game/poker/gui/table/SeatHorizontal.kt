package game.poker.gui.table

class SeatHorizontal(val positionNumber:Int) : SeatBase(){
    override fun updateCardsPosition(isCardsUp: Boolean){
        if (isCardsUp){
            card1.setPosition(0f,0f)
            card2.setPosition(20f, -20f)
        } else {
            card1.y = 0f
            card2.y = 0f
            if(positionNumber == 0){
                card1.x = -80f
                card2.x = 80f
                return
            }
            if (positionNumber == 6 || positionNumber == 7 || positionNumber == 8){
                card1.x = -50f
                card2.x = 50f
            }
            else {
                card2.x = 110f
            }
        }
    }

    init {
        when(positionNumber){
            0 -> {
                setPosition(120f, 1000f)
                chipstack.setPosition(50f, 300f)
            }
            1 -> {
                setPosition(170f, 1900f)
                chipstack.setPosition(290f, 160f)
            }
            2 -> {
                setPosition(570f, 1900f)
                chipstack.setPosition(210f, 90f)
            }
            3 -> {
                setPosition(900f, 1600f)
                chipstack.setPosition(80f, -90f)
            }
            4 -> {
                setPosition(900f, 1200f)
                chipstack.setPosition(0f, -100f)
            }
            5 -> {
                setPosition(900f, 800f)
                chipstack.setPosition(0f, -100f)
            }
            6 -> {
                setPosition(900f, 400f)
                chipstack.setPosition(-150f, -90f)
            }
            7 -> {
                setPosition(600f, 200f)
                chipstack.setPosition(-260f, 60f)
            }
            8 -> {
                setPosition(300f, 200f)
                chipstack.setPosition(-260f, 60f)
            }
            else -> throw IllegalArgumentException("Bad index")
        }
        playerView.setPosition(0f, 150f)
        when(positionNumber){
            0 -> {
                playerView.x = -350f
                playerView.y = 0f
            }
            6, 7, 8 -> {
                playerView.x = -100f
            }
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
        chipstack.setChips(9999)
        rotation = -90f
    }
}
