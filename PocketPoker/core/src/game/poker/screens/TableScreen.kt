package game.poker.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import game.poker.PocketPoker
import game.poker.gui.Seat
import game.poker.core.Rank
import game.poker.core.Suit
import game.poker.core.Card
import game.poker.gui.Pot
import game.poker.core.Visibility
import game.poker.staticFiles.Textures
import game.poker.Settings
import game.poker.staticFiles.Fonts


class TableScreen(val game: PocketPoker) : BaseScreen {

    var isLandscape = false
        private set
    var isFinal = false
    // describes is this table is final or not
    // note: in final table all empty seats are hidden
    //     but in other tables all empty seats just shows with text "Empty seat"
    private val stage = Stage(game.view)
    private val seats = Array(9) { i -> Seat(i) }
    private val pot = Pot()
    private val exitButton = ImageButton(SpriteDrawable(Sprite(Textures.exitButton)),
            SpriteDrawable(Sprite(Textures.exitButtonDown)))
    private val cards = Array(5){Image(SpriteDrawable(Sprite(Textures.cardPlaceholder)))}
    private val chatButton:TextButton
    private val infoButton:TextButton
    private val foldButton:TextButton
    private val callButton:TextButton
    private val raiseButton:TextButton

    init {
        val tableSprite = Sprite(Textures.pokerTable)
        tableSprite.rotate90(true)
        val pokerTable = Image(SpriteDrawable(tableSprite))
        pokerTable.setPosition(game.gameWidth * 0.1f, game.gameHeight * 0.1f)
        pokerTable.setSize(game.gameWidth * 0.8f, game.gameHeight * 0.8f)
        val buttonSprite = SpriteDrawable(Sprite(Textures.menuButton))
        val buttonDownSprite = SpriteDrawable(Sprite(Textures.menuButtonDown))
        val buttonStyle = TextButton.TextButtonStyle(buttonSprite, buttonDownSprite, buttonSprite,
                Fonts.mainMenuButtonFont)
        chatButton = TextButton(Settings.getText(Settings.TextKeys.CHAT), buttonStyle)
        infoButton = TextButton(Settings.getText(Settings.TextKeys.INFO), buttonStyle)
        foldButton = TextButton(Settings.getText(Settings.TextKeys.FOLD), buttonStyle)
        callButton = TextButton(Settings.getText(Settings.TextKeys.CALL), buttonStyle)
        raiseButton = TextButton(Settings.getText(Settings.TextKeys.RAISE), buttonStyle)
        setUpButtons()
        setUpCards()

        //DEBUG
        setFlopCards(Card(Rank.Ten,Suit.Clubs, Visibility.Open),Card(Rank.Ace,Suit.Spades, Visibility.Open),Card(Rank.Ace,Suit.Clubs, Visibility.Open))
        setTurnCard(Card(Rank.Five,Suit.Clubs, Visibility.Open))
        setRiverCard(Card(Rank.Two,Suit.Clubs, Visibility.Open))
        setEmptyPlayer(2)
        dealCards()
        setChips(1,999)
        updatePlayerInfo(1,"Николай","999","")
        setPlayerCards(1, Card(Rank.Ace,Suit.Hearts, Visibility.Open), Card(Rank.Ace,Suit.Diamonds, Visibility.Open))
        setPotChips(999999)
        setPotCount("999 999")
        //END DEBUG

        stage.addActor(pokerTable)
        seats.forEach { stage.addActor(it) }
        cards.forEach { stage.addActor(it) }
        stage.addActor(pot)
        stage.addActor(chatButton)
        stage.addActor(infoButton)
        stage.addActor(foldButton)
        stage.addActor(callButton)
        stage.addActor(raiseButton)
        stage.addActor(exitButton)
    }

    private fun setUpCards() {
        cards.forEach { it.setSize(100f,140f) }
        cards[0].setPosition(400f,1100f)
        cards[1].setPosition(510f,1100f)
        cards[2].setPosition(620f,1100f)
        cards[3].setPosition(450f,950f)
        cards[4].setPosition(560f,950f)
    }

    private fun setUpButtons(){
        exitButton.addListener(game.switches[ScreenType.MAIN_MENU])
        exitButton.setPosition(10f,1790f)
        exitButton.setSize(120f,120f)
        chatButton.setPosition(850f,300f)
        chatButton.setSize(200f,90f)
        infoButton.setPosition(850f,150f)
        infoButton.setSize(200f,90f)
        foldButton.setPosition(20f,20f)
        foldButton.setSize(200f,90f)
        callButton.setPosition(240f,20f)
        callButton.setSize(200f,90f)
        raiseButton.setPosition(460f,20f)
        raiseButton.setSize(200f,90f)
    }

    override fun update(){
        pot.update()
        seats.forEach { it.update() }
        chatButton.setText(Settings.getText(Settings.TextKeys.CHAT))
        infoButton.setText(Settings.getText(Settings.TextKeys.INFO))
        foldButton.setText(Settings.getText(Settings.TextKeys.FOLD))
        callButton.setText(Settings.getText(Settings.TextKeys.CALL))
        raiseButton.setText(Settings.getText(Settings.TextKeys.RAISE))
    }

    override fun show(){
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        stage.act()
        stage.draw()
    }

    override fun resize(width: Int, height: Int){

    }

    override fun pause(){

    }

    override fun resume(){

    }

    override fun hide(){

    }

    override fun dispose(){

    }


    // all events that received from handler

    fun initTable(){

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
    }

    fun switchDecision(localSeat: Int){
        // set yellow background to other player
        seats[localSeat-1].playerView.isActive = true
    }

    fun dealCards(){
        // set to all players background card texture
        seats.forEach { if(!it.isEmpty) it.upCards() }
    }

    fun hideCards(localSeat: Int){
        // if cards are known then just set hidden texture
        // else set to placeholder
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

    fun setPlayerDisconnected(localSeat: Int, isDiscinnected: Boolean){

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

    }

    fun setPlayer(localSeat: Int, isDisconnected: Boolean,
                  name: String, stack: String){
        seats[localSeat-1].playerView.playerName = name
        seats[localSeat-1].playerView.money = stack
        seats[localSeat-1].playerView.info = ""
        seats[localSeat-1].isEmpty = false
    }

    fun setEmptyPlayer(localSeat: Int){
        seats[localSeat-1].playerView.playerName = Settings.getText(Settings.TextKeys.EMPTY_PLAYER) + "" //String? -> String
        seats[localSeat-1].playerView.money = ""
        seats[localSeat-1].playerView.info = ""
        seats[localSeat-1].isEmpty = true
        seats[localSeat-1].clearCards()
        setChips(localSeat, 0)
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
    }

    fun updatePlayerInfo(localSeat: Int, name: String,
                         stack: String, info: String){
        seats[localSeat-1].playerView.playerName = name
        seats[localSeat-1].playerView.money = stack
        seats[localSeat-1].playerView.info = info
        seats[localSeat-1].isEmpty = false
    }


}