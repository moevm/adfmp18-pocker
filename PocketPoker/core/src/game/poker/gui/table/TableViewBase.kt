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
import com.google.gson.JsonObject
import game.poker.Settings
import game.poker.core.Card
import game.poker.core.Visibility
import game.poker.screens.BaseScreen
import game.poker.staticFiles.Fonts
import game.poker.staticFiles.Textures
import game.poker.PocketPoker
import game.poker.screens.ScreenType
import game.poker.core.Rank
import game.poker.core.Suit
import game.poker.core.handle.GameHandler
import game.poker.core.handle.ReplayHandler
import game.poker.screens.TableScreen


abstract class TableViewBase(val game: PocketPoker, val table: TableScreen) : BaseScreen {
    val stage = Stage(game.view)
    var mode = Settings.currTableMode
        set(value) {
            field = value
            updateButtons()
        }
    var isFinal = false
        set(value) {
            field = value
            seats.forEach {
                if (it.isEmpty) {
                    it.isVisible = !isFinal
                }
            }
        }
    protected val seats = mutableListOf<SeatBase>()
    protected val pot = Pot()
    protected val cards = Array(5){ Image(SpriteDrawable(Sprite(Textures.cardPlaceholder))) }
    private val cardsObjects = Array(5) { Card(Rank.Ace, Suit.Hearts, Visibility.Open) }
    protected val chatButton= ImageButton(SpriteDrawable(Sprite(Textures.chatButton)),
            SpriteDrawable(Sprite(Textures.chatButtonDown)))
    protected val infoButton= ImageButton(SpriteDrawable(Sprite(Textures.infoButton)),
            SpriteDrawable(Sprite(Textures.infoButtonDown)))
    protected val leftChoiceButton: TextButton
    protected val cenralChoiceButton: TextButton
    protected val rightChoiceButton: TextButton
    protected val exitButton = ImageButton(SpriteDrawable(Sprite(Textures.exitButton)),
            SpriteDrawable(Sprite(Textures.exitButtonDown)))
    protected val nextHandButton = ImageButton(SpriteDrawable(Sprite(Textures.nextHand)),
            SpriteDrawable(Sprite(Textures.nextHandDown)))
    protected val nextStepButton = ImageButton(SpriteDrawable(Sprite(Textures.nextStep)),
            SpriteDrawable(Sprite(Textures.nextStepDown)))
    protected val prevHandButton = ImageButton(SpriteDrawable(Sprite(Textures.prevHand)),
            SpriteDrawable(Sprite(Textures.prevHandDown)))
    protected val pausePlayButton = ImageButton(SpriteDrawable(Sprite(Textures.pauseButton)),
            SpriteDrawable(Sprite(Textures.pauseButtonDown)))
    private val bg =  Image(Textures.menuBg)
    protected val handler = object: RaiseDialogBase.RaiseResultHandler() {
        override fun handle(raiseValue: Long) {
            //вызывается, когда пользователь нажал ОК на диалоге рейза
            //raiseValue - выбранное им значение рейза
            println("Raise " + raiseValue.toString())
            val gameHandler = table.handler
            if(gameHandler is GameHandler){
                gameHandler.sendRaise(raiseValue)
            }
        }
    }
    class RaiseInfo {
        var minRaise: Long = 10
        var maxRaise: Long = 1000
        var raiseStep: Long = 10
        var pot: Long = 500
    }
    val raiseInfo = RaiseInfo()

    var needUpdateFlop = false
        private set
    var needUpdateTurn = false
        private set
    var needUpdateRiver = false
        private set
    class Point {
        var x = 0f
        var y = 0f
        fun setLocation(nx:Float, ny:Float) {
            x = nx
            y = ny
        }
        fun setLocation(nx:Double, ny:Double) {
            x = nx.toFloat()
            y = ny.toFloat()
        }
    }

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
        leftChoiceButton = TextButton(Settings.getText(Settings.TextKeys.FOLD), buttonStyle)
        cenralChoiceButton = TextButton(Settings.getText(Settings.TextKeys.CALL), buttonStyle)
        rightChoiceButton = TextButton(Settings.getText(Settings.TextKeys.RAISE), buttonStyle)
        chatButton.isTransform = true
        infoButton.isTransform = true
        leftChoiceButton.isTransform = true
        cenralChoiceButton.isTransform = true
        rightChoiceButton.isTransform = true
        exitButton.isTransform = true
        nextHandButton.isTransform = true
        nextStepButton.isTransform = true
        prevHandButton.isTransform = true
        pausePlayButton.isTransform = true
        pausePlayButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                if (pausePlayButton.isChecked) {
                    pausePlayButton.style.imageUp = SpriteDrawable(Sprite(Textures.pauseButton))
                    pausePlayButton.style.imageDown = SpriteDrawable(Sprite(Textures.pauseButtonDown))
                    nextStepButton.isVisible = false
                    val replayHandler = table.handler
                    if(replayHandler is ReplayHandler){
                        replayHandler.pausePlay()
                    }
                } else {
                    pausePlayButton.style.imageUp = SpriteDrawable(Sprite(Textures.playButton))
                    pausePlayButton.style.imageDown = SpriteDrawable(Sprite(Textures.playButtonDown))
                    nextStepButton.isVisible = true
                    val replayHandler = table.handler
                    if(replayHandler is ReplayHandler){
                        replayHandler.pausePlay()
                    }
                }
            }
        })
        prevHandButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                resetTable()
                val replayHandler = table.handler
                if(replayHandler is ReplayHandler){
                    replayHandler.prevHand()
                }
            }
        })
        nextHandButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                resetTable()
                val replayHandler = table.handler
                if(replayHandler is ReplayHandler){
                    replayHandler.nextHand()
                }
            }
        })
        nextStepButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                val replayHandler = table.handler
                if(replayHandler is ReplayHandler){
                    replayHandler.nextStep()
                }
            }
        })
        leftChoiceButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                val gameHandler = table.handler
                if(gameHandler is GameHandler){
                    gameHandler.sendFold()
                }
            }
        })
        cenralChoiceButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                val gameHandler = table.handler
                if(gameHandler is GameHandler){
                    gameHandler.sendMiddle()
                }
            }
        })
        stage.addActor(bg)
        stage.addActor(pokerTable)
        stage.addActor(pot)
        cards.forEach { stage.addActor(it) }
        stage.addActor(chatButton)
        stage.addActor(infoButton)
        stage.addActor(leftChoiceButton)
        stage.addActor(cenralChoiceButton)
        stage.addActor(rightChoiceButton)
        stage.addActor(nextHandButton)
        stage.addActor(nextStepButton)
        stage.addActor(prevHandButton)
        stage.addActor(pausePlayButton)
        stage.addActor(exitButton)
        updateButtons()
        resetTable()
    }

    override fun update() {
        leftChoiceButton.setText(Settings.getText(Settings.TextKeys.FOLD))
        cenralChoiceButton.setText(Settings.getText(Settings.TextKeys.CALL))
        rightChoiceButton.setText(Settings.getText(Settings.TextKeys.RAISE))
        pot.update()
        seats.forEach { it.update() }
    }

    override fun render(delta: Float) {
        if(needUpdateFlop){
            updateFlopCards()
        }
        if(needUpdateTurn){
            updateTurnCard()
        }
        if(needUpdateRiver){
            updateRiverCard()
        }
        if(pot.chipstack.needUpdateChips){
            pot.chipstack.updateChips()
        }
        seats.forEach {
            it.checkUpdates()
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
        clearInDecision()
        setPotCount("")
        setPotChips(0L)
        clearAllCards()
        for (i in 1..9) {
            setEmptyPlayer(i)
            setChips(i,0)
        }
        seats.forEach { it.isVisible = false }
    }

    fun fit(table:TableViewBase) {
        if(Gdx.input.inputProcessor == table.stage) {
            Gdx.input.inputProcessor = stage
        }
        for ((i, seat) in seats.withIndex()){
            seat.fit(table.seats[i])
        }
        pot.money = table.pot.money
        pot.count = table.pot.count
        for ((i,card) in cards.withIndex()) {
            card.drawable = table.cards[i].drawable
        }
        raiseInfo.minRaise = table.raiseInfo.minRaise
        raiseInfo.maxRaise = table.raiseInfo.maxRaise
        raiseInfo.raiseStep = table.raiseInfo.raiseStep
        raiseInfo.pot = table.raiseInfo.pot
    }

    private fun updateButtons(){
        leftChoiceButton.isVisible = false
        cenralChoiceButton.isVisible = false
        rightChoiceButton.isVisible = false
        nextHandButton.isVisible = false
        nextStepButton.isVisible = false
        prevHandButton.isVisible = false
        pausePlayButton.isVisible = false
        when (mode){
            Settings.TableMode.Game -> {

            }
            Settings.TableMode.Replay -> {
                nextHandButton.isVisible = true
                prevHandButton.isVisible = true
                pausePlayButton.isVisible = true

                nextStepButton.isVisible = false
                pausePlayButton.isChecked = true
            }
            Settings.TableMode.Spectate -> {

            }
        }
    }

    protected fun getPotPosition(): Point {
        val point = Point()
        point.setLocation(pot.x, pot.y)
        return point
    }

    fun resetTable() {
        pot.actions.clear()
        setPotChips(0L)
        seats.forEach {
            it.actions.clear()
            it.resetChips()
        }
        clearAllCards()
        clearInDecision()
        removeDecisions()
    }

    // all events that received from handler

    fun initTable(){

    }

    fun moveChipsToPot(){
        var potMoney = 0L
        seats.forEach {
            it.moveChipsToPot()
            potMoney += it.getChips()
        }
        pot.setMoneyAfterAnimation(potMoney)
    }

    fun moveChipsFromPot( localSeat: Int, amount: Long){
        seats[localSeat - 1].setChips(amount)
        seats[localSeat - 1].moveChipsFromPot()
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
        cardsObjects[0] = card1
        cardsObjects[1] = card2
        cardsObjects[2] = card3
        needUpdateFlop = true
    }

    fun updateFlopCards(){
        cards[0].drawable = SpriteDrawable(Sprite(Textures.getCard(cardsObjects[0])))
        cards[1].drawable = SpriteDrawable(Sprite(Textures.getCard(cardsObjects[1])))
        cards[2].drawable = SpriteDrawable(Sprite(Textures.getCard(cardsObjects[2])))
        needUpdateFlop = false
    }

    fun setTurnCard(card: Card){
        cardsObjects[3] = card
        needUpdateTurn = true
    }

    fun updateTurnCard(){
        cards[3].drawable = SpriteDrawable(Sprite(Textures.getCard(cardsObjects[3])))
        needUpdateTurn = false
    }

    fun setRiverCard(card: Card){
        cardsObjects[4] = card
        needUpdateRiver = true
    }

    fun updateRiverCard(){
        cards[4].drawable = SpriteDrawable(Sprite(Textures.getCard(cardsObjects[4])))
        needUpdateRiver = false
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
        seats[localSeat-1].playerView.isDisabled = isDisconnected
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

    fun removePlayingChoices(){
        // Убирает две или три кнопки выбора текущео решения
        rightChoiceButton.isVisible = false
        cenralChoiceButton.isVisible = false
        leftChoiceButton.isVisible = false
    }

    fun setPlayingChoices(choices: List<String>){
        // Наступил ход игрока. Показать варианты выбора
        // может быть длины 2 или 3
        for ((i, text) in choices.withIndex()) {
            when (i) {
                0 -> {
                    leftChoiceButton.setText(text)
                    leftChoiceButton.isVisible = true
                }
                1 -> {
                    cenralChoiceButton.setText(text)
                    cenralChoiceButton.isVisible = true
                }
                2 -> {
                    rightChoiceButton.setText(text)
                    rightChoiceButton.isVisible = true
                }
            }
        }
    }

    fun setRaiseInfo(minRaise: Long, maxRaise: Long, step: Long, pot: Long){
        raiseInfo.minRaise = minRaise
        raiseInfo.maxRaise = maxRaise
        raiseInfo.raiseStep = step
        raiseInfo.pot = pot
    }

    override fun receiveFromServer(json: JsonObject) {

    }
}
