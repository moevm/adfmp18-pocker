package game.poker.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import game.poker.PocketPoker
import game.poker.gui.Seat
import game.poker.core.Rank
import game.poker.core.Suit
import game.poker.core.Card
import game.poker.gui.Pot
import game.poker.core.Visibility
import game.poker.staticFiles.Textures


class TableScreen(val game: PocketPoker) : BaseScreen {

    private var isLandscape = false
        private set
    var isFinal = false
    // describes is this table is final or not
    // note: in final table all empty seats are hidden
    //     but in other tables all empty seats just shows with text "Empty seat"
    private val stage = Stage(game.view)
    private var seats = Array(9) { i -> Seat(i) }
    private val pot = Pot()
    private val exitButton = ImageButton(SpriteDrawable(Sprite(Textures.exitButton)),
            SpriteDrawable(Sprite(Textures.exitButtonDown)))
    private val cards = Array(5){Image(SpriteDrawable(Sprite(Textures.cardPlaceholder)))}//cardBackground cardPlaceholder

    init {
        exitButton.addListener(game.switches[ScreenType.MAIN_MENU])
        exitButton.setPosition(10f,1790f)
        exitButton.setSize(120f,120f)
        val tableSprite = Sprite(Textures.pokerTable)
        tableSprite.setSize(game.gameWidth * 0.8f,game.gameHeight * 0.8f)
        tableSprite.rotate90(true)
        val pokerTable = Image(SpriteDrawable(tableSprite))
        pokerTable.x = game.gameWidth * 0.1f
        pokerTable.y = game.gameHeight * 0.1f
        cards.forEach { it.setSize(100f,140f) }
        cards[0].setPosition(400f,1100f)
        cards[1].setPosition(510f,1100f)
        cards[2].setPosition(620f,1100f)
        cards[3].setPosition(450f,950f)
        cards[4].setPosition(560f,950f)
        stage.addActor(pokerTable)
        seats[0].setCards(Card(Rank.Ace,Suit.Clubs, Visibility.Open),Card(Rank.Jack,Suit.Diamonds, Visibility.Open))
        seats.forEach {
            it.setCards(Card(Rank.Ace,Suit.Clubs, Visibility.Open),Card(Rank.Jack,Suit.Diamonds, Visibility.Open))
            //it.upCards()
            //it.playerView.playerName = "Name"
            //it.playerView.money = "999 999"
            //it.playerView.info = ""
            stage.addActor(it)
        }
        cards.forEach {
            it.setSize(100f,140f)
            it.drawable = SpriteDrawable(Sprite(Textures.getCard(Card(Rank.Ace,Suit.Hearts, Visibility.Open))))
            stage.addActor(it)
        }
        stage.addActor(pot)
        stage.addActor(exitButton)
    }

    override fun update(){
        pot.update()
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
    }

    fun dealCards(){
        // set to all players background card texture
    }

    fun hideCards(localSeat: Int){
        // if cards are known then just set hidden texture
        // else set to placeholder
    }

    fun deleteCards(localSeat: Int){
        // always set to placeholder
    }

    fun setFlopCards(card1: Card, card2: Card, card3: Card){

    }

    fun setTurnCard(card: Card){

    }

    fun setRiverCard(card: Card){

    }

    fun setPlayerCards(localSeat: Int, card1: Card, card2: Card){

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

    }

    fun setEmptyPlayer(localSeat: Int){

    }

    fun clearInDecision(){
        // remove yellow background from active player
    }

    fun setPotCount(potCount: String){

    }

    fun setChips(localSeat: Int, count: Long){

    }

    fun setPotChips(count: Long){

    }

    fun clearAllCards(){
        // set all cards to placeholder
    }

    fun updatePlayerInfo(localSeat: Int, name: String,
                         stack: String, info: String){

    }


}