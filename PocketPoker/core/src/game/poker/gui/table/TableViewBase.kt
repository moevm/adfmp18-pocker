package game.poker.gui.table

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import game.poker.Settings
import game.poker.core.Card
import game.poker.core.Visibility
import game.poker.screens.BaseScreen
import game.poker.staticFiles.Fonts
import game.poker.staticFiles.Textures
import game.poker.PocketPoker
import game.poker.screens.ScreenType
import game.poker.core.handle.Handler


abstract class TableViewBase(val game: PocketPoker) : BaseScreen {
    val stage = Stage(game.view)
    var mode = Handler.Mode.Replay
        set(value) {
            field = value
            updateButtons()
        }
    var isFinal = false
    protected val seats = mutableListOf<SeatBase>()
    protected val pot = Pot()
    protected val cards = Array(5){ Image(SpriteDrawable(Sprite(Textures.cardPlaceholder))) }
    protected val chatButton= ImageButton(SpriteDrawable(Sprite(Textures.chatButton)),
            SpriteDrawable(Sprite(Textures.chatButtonDown)))
    protected val infoButton= ImageButton(SpriteDrawable(Sprite(Textures.infoButton)),
            SpriteDrawable(Sprite(Textures.infoButtonDown)))
    protected val foldButton: TextButton
    protected val callButton: TextButton
    protected val raiseButton: TextButton
    protected val exitButton = ImageButton(SpriteDrawable(Sprite(Textures.exitButton)),
            SpriteDrawable(Sprite(Textures.exitButtonDown)))
    protected val nextHandButton = ImageButton(SpriteDrawable(Sprite(Textures.nextHand)),
            SpriteDrawable(Sprite(Textures.nextHandDown)))
    protected val nextStepButton = ImageButton(SpriteDrawable(Sprite(Textures.nextStep)),
            SpriteDrawable(Sprite(Textures.nextStepDown)))
    protected val prevHandButton = ImageButton(SpriteDrawable(Sprite(Textures.prevHand)),
            SpriteDrawable(Sprite(Textures.prevHandDown)))
    protected val pausePlayButton = ImageButton(SpriteDrawable(Sprite(Textures.playButton)),
            SpriteDrawable(Sprite(Textures.playButtonDown)))
    val gauss = arrayListOf<Float>( 0.0F, 0.001F, 0.002F, 0.003F, 0.004F, 0.006F, 0.009F, 0.013F, 0.018F, 0.024F,
            0.032F, 0.042F, 0.054F, 0.068F, 0.085F, 0.105F, 0.128F, 0.155F, 0.185F, 0.219F,
            0.256F, 0.296F, 0.339F, 0.384F, 0.43F, 0.477F, 0.524F, 0.571F, 0.617F, 0.662F,
            0.705F, 0.745F, 0.782F, 0.816F, 0.846F, 0.873F, 0.896F, 0.916F, 0.933F, 0.947F,
            0.959F, 0.969F, 0.977F, 0.983F, 0.988F, 0.992F, 0.995F, 0.997F, 0.999F, 1.0F)
    private var chipsIsMovingToPot = false
    private var chipsIsMovingFromPot = false
    private var moveStep = 0

    init {
        val tableSprite = Sprite(Textures.pokerTable)
        tableSprite.rotate90(true)
        val pokerTable = Image(SpriteDrawable(tableSprite))
        pokerTable.setPosition(game.gameWidth * 0.05f, game.gameHeight * 0.05f)
        pokerTable.setSize(game.gameWidth * 0.9f, game.gameHeight * 0.9f)
        exitButton.addListener(game.switches[ScreenType.MAIN_MENU])
        exitButton.setSize(120f, 120f)
        val buttonSprite = SpriteDrawable(Sprite(Textures.menuButton))
        val buttonDownSprite = SpriteDrawable(Sprite(Textures.menuButtonDown))
        val buttonStyle = TextButton.TextButtonStyle(buttonSprite, buttonDownSprite, buttonSprite,
                Fonts.mainMenuButtonFont)
        foldButton = TextButton(Settings.getText(Settings.TextKeys.FOLD), buttonStyle)
        callButton = TextButton(Settings.getText(Settings.TextKeys.CALL), buttonStyle)
        raiseButton = TextButton(Settings.getText(Settings.TextKeys.RAISE), buttonStyle)
        chatButton.isTransform = true
        infoButton.isTransform = true
        foldButton.isTransform = true
        callButton.isTransform = true
        raiseButton.isTransform = true
        exitButton.isTransform = true
        nextHandButton.isTransform = true
        nextStepButton.isTransform = true
        prevHandButton.isTransform = true
        pausePlayButton.isTransform = true
        pausePlayButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                //DEBUG
                moveChipsToPot()
                //END DEBUG
                if (pausePlayButton.isChecked) {
                    pausePlayButton.style.imageUp = SpriteDrawable(Sprite(Textures.pauseButton))
                    pausePlayButton.style.imageDown = SpriteDrawable(Sprite(Textures.pauseButtonDown))
                    nextStepButton.isVisible = false
                } else {
                    pausePlayButton.style.imageUp = SpriteDrawable(Sprite(Textures.playButton))
                    pausePlayButton.style.imageDown = SpriteDrawable(Sprite(Textures.playButtonDown))
                    nextStepButton.isVisible = true
                }
            }
        })
        prevHandButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                //DEBUG
                moveChipsFromPot(1,pot.money - 599)
                moveChipsFromPot(2,599)
                //END DEBUG
            }
        })
        nextHandButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                //DEBUG
                for (i in 1..9) {
                    setChips(i,9999)
                }
                //END DEBUG
            }
        })
        stage.addActor(pokerTable)
        stage.addActor(pot)
        cards.forEach { stage.addActor(it) }
        stage.addActor(chatButton)
        stage.addActor(infoButton)
        stage.addActor(foldButton)
        stage.addActor(callButton)
        stage.addActor(raiseButton)
        stage.addActor(nextHandButton)
        stage.addActor(nextStepButton)
        stage.addActor(prevHandButton)
        stage.addActor(pausePlayButton)
        stage.addActor(exitButton)
        updateButtons()
    }

    override fun update() {
        foldButton.setText(Settings.getText(Settings.TextKeys.FOLD))
        callButton.setText(Settings.getText(Settings.TextKeys.CALL))
        raiseButton.setText(Settings.getText(Settings.TextKeys.RAISE))
        pot.update()
        seats.forEach { it.update() }
    }

    override fun render(delta: Float) {
        if (chipsIsMovingToPot) {
            seats.forEach { it.moveChipsToPot(gauss[moveStep]) }
            moveStep += 1
            if (moveStep > 49) {
                chipsIsMovingToPot = false
                seats.forEach {
                    it.moveChipsToPot(gauss[0])
                    pot.money += it.getChips()
                    it.setChips(0)
                }
            }
        }
        if (chipsIsMovingFromPot) {
            seats.forEach { it.moveChipsToPot(gauss[moveStep]) }
            moveStep -= 1
            if (moveStep < 0) {
                chipsIsMovingFromPot = false
            }
        }
        stage.act()
        stage.draw()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {

    }

    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun hide() {

    }

    fun fit(table:TableViewBase) {
        if(Gdx.input.inputProcessor == table.stage) {
            Gdx.input.inputProcessor = stage
        }
        for ((i,seat) in seats.withIndex()){
            seat.playerView.money = table.seats[i].playerView.money
            seat.playerView.playerName = table.seats[i].playerView.playerName
            seat.playerView.info = table.seats[i].playerView.info
            seat.setChips(table.seats[i].getChips())
            seat.isDealer = table.seats[i].isDealer
            seat.isEmpty = table.seats[i].isEmpty
            if (table.seats[i].isCardsUp) {
                seat.upCards()
            } else {
                seat.setCards(table.seats[i].cardName1,table.seats[i].cardName2)
            }
            if (table.seats[i].isCardsEmpty) {
                seat.clearCards()
            }
            seat.playerView.isDisabled = table.seats[i].playerView.isDisabled
            seat.playerView.isActive = table.seats[i].playerView.isActive
            seat.isVisible = table.seats[i].isVisible
        }
        pot.money = table.pot.money
        pot.count = table.pot.count
        for ((i,card) in cards.withIndex()) {
            card.drawable = table.cards[i].drawable
        }
    }

    private fun updateButtons(){
        foldButton.isVisible = false
        callButton.isVisible = false
        raiseButton.isVisible = false
        nextHandButton.isVisible = false
        nextStepButton.isVisible = false
        prevHandButton.isVisible = false
        pausePlayButton.isVisible = false
        when (mode){
            Handler.Mode.Game -> {
                foldButton.isVisible = true
                callButton.isVisible = true
                raiseButton.isVisible = true
            }
            Handler.Mode.Replay -> {
                nextHandButton.isVisible = true
                nextStepButton.isVisible = true
                prevHandButton.isVisible = true
                pausePlayButton.isVisible = true
            }
            Handler.Mode.Spectate -> {

            }
        }
    }

    // all events that received from handler

    fun initTable(){

    }

    fun moveChipsToPot(){
        chipsIsMovingFromPot = false
        moveStep = 0
        chipsIsMovingToPot = true
    }

    fun moveChipsFromPot( localSeat: Int, amount: Long){
        chipsIsMovingToPot = false
        pot.money -= amount
        seats[localSeat].setChips(amount)
        moveStep = 49
        chipsIsMovingFromPot = true
    }

    fun setTableNum(tableNum: String){

    }

    fun setHandNum(handNum: String){

    }

    fun setBlinds(smallBlind: String, bigBlind: String, ante: String){

    }

    fun setAverageStack(averageStack: String){

    }

    fun setPlayersLeft(playersLeft: String){

    }

    fun setTopPlayers(topPlayers: List<Pair<String, String>>){

    }

    fun setDealerPos(localSeat: Int){
        // set dealer chip
        seats.forEach { it.isDealer = false }
        seats[localSeat-1].isDealer = true
    }

    fun switchDecision(localSeat: Int){
        // set yellow background to other player
        clearInDecision()
        seats[localSeat-1].playerView.isActive = true
    }

    fun dealCards(){
        // set to all players background card texture
        seats.forEach { if(!it.isEmpty) it.upCards() }
    }

    fun hideCards(localSeat: Int){
        // if cards are known then just set hidden texture
        // else set to placeholder
        if (!seats[localSeat-1].isEmpty && !seats[localSeat-1].isCardsUp && !seats[localSeat-1].isCardsEmpty) {
            val card1 = seats[localSeat-1].cardName1
            val card2 = seats[localSeat-1].cardName2
            card1.visibility = Visibility.Hidden
            card2.visibility = Visibility.Hidden
            seats[localSeat-1].setCards(card1,card2)
        } else {
            seats[localSeat-1].clearCards()
        }
    }

    fun deleteCards(localSeat: Int){
        // always set to placeholder
        seats[localSeat-1].clearCards()
    }

    fun setFlopCards(card1: Card, card2: Card, card3: Card){
        cards[0].drawable = SpriteDrawable(Sprite(Textures.getCard(card1)))
        cards[1].drawable = SpriteDrawable(Sprite(Textures.getCard(card2)))
        cards[2].drawable = SpriteDrawable(Sprite(Textures.getCard(card3)))
    }

    fun setTurnCard(card: Card){
        cards[3].drawable = SpriteDrawable(Sprite(Textures.getCard(card)))
    }

    fun setRiverCard(card: Card){
        cards[4].drawable = SpriteDrawable(Sprite(Textures.getCard(card)))
    }

    fun setPlayerCards(localSeat: Int, card1: Card, card2: Card){
        seats[localSeat-1].setCards(card1,card2)
    }

    fun setCurrPlace(place: String){

    }

    fun addToChat(text: String){

    }

    fun setPlayerDisconnected(localSeat: Int, isDisconnected: Boolean){
        seats[localSeat-1].playerView.isDisabled = isDisconnected
    }

    fun setPremoves(isVisible: Boolean){

    }

    fun setPremoveText(option1: String, option2: String, option3: String){

    }

    fun setThirdPremoveHidden(isHidden: Boolean){

    }

    fun setPremovesChecked(option1: Boolean, option2: Boolean, option3: Boolean){

    }

    fun setPlaceInfo(place: String, outOf: String){

    }

    fun removeDecisions(){
        seats.forEach { it.playerView.info = "" }
    }

    fun setPlayer(localSeat: Int, isDisconnected: Boolean,
                  name: String, stack: String){
        seats[localSeat-1].playerView.playerName = name
        seats[localSeat-1].playerView.money = stack
        seats[localSeat-1].playerView.info = ""
        seats[localSeat-1].isEmpty = false
        seats[localSeat-1].isVisible = true
    }

    fun setEmptyPlayer(localSeat: Int){
        seats[localSeat-1].isEmpty = true
        seats[localSeat-1].isVisible = !isFinal
    }

    fun clearInDecision(){
        // remove yellow background from active player
        seats.forEach { it.playerView.isActive = false }
    }

    fun setPotCount(potCount: String){
        pot.count = potCount
    }

    fun setChips(localSeat: Int, count: Long){
        seats[localSeat-1].setChips(count)
    }

    fun setPotChips(count: Long){
        pot.money = count
    }

    fun clearAllCards(){
        // set all cards to placeholder
        seats.forEach { it.clearCards() }
        cards.forEach { it.drawable = SpriteDrawable(Sprite(Textures.cardPlaceholder)) }
    }

    fun updatePlayerInfo(localSeat: Int, name: String,
                         stack: String, info: String){
        seats[localSeat-1].playerView.playerName = name
        seats[localSeat-1].playerView.money = stack
        seats[localSeat-1].playerView.info = info
        seats[localSeat-1].isEmpty = false
    }
}
