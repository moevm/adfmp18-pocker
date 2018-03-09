package game.poker.gui.table

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import game.poker.Settings
import game.poker.core.Card
import game.poker.screens.BaseScreen
import game.poker.staticFiles.Fonts
import game.poker.staticFiles.Textures
import game.poker.PocketPoker
import game.poker.screens.ScreenType


abstract class TableViewBase(val game: PocketPoker) : BaseScreen {
    val stage = Stage(game.view)
    protected val seats = mutableListOf<SeatBase>()
    protected val pot = Pot()
    protected val cards = Array(5){ Image(SpriteDrawable(Sprite(Textures.cardPlaceholder))) }
    protected val chatButton: TextButton
    protected val infoButton: TextButton
    protected val foldButton: TextButton
    protected val callButton: TextButton
    protected val raiseButton: TextButton
    protected val exitButton = ImageButton(SpriteDrawable(Sprite(Textures.exitButton)),
            SpriteDrawable(Sprite(Textures.exitButtonDown)))

    init {
        val tableSprite = Sprite(Textures.pokerTable)
        tableSprite.rotate90(true)
        val pokerTable = Image(SpriteDrawable(tableSprite))
        pokerTable.setPosition(game.gameWidth * 0.1f, game.gameHeight * 0.1f)
        pokerTable.setSize(game.gameWidth * 0.8f, game.gameHeight * 0.8f)
        exitButton.addListener(game.switches[ScreenType.MAIN_MENU])
        exitButton.setSize(120f, 120f)
        val buttonSprite = SpriteDrawable(Sprite(Textures.menuButton))
        val buttonDownSprite = SpriteDrawable(Sprite(Textures.menuButtonDown))
        val buttonStyle = TextButton.TextButtonStyle(buttonSprite, buttonDownSprite, buttonSprite,
                Fonts.mainMenuButtonFont)
        chatButton = TextButton(Settings.getText(Settings.TextKeys.CHAT), buttonStyle)
        infoButton = TextButton(Settings.getText(Settings.TextKeys.INFO), buttonStyle)
        foldButton = TextButton(Settings.getText(Settings.TextKeys.FOLD), buttonStyle)
        callButton = TextButton(Settings.getText(Settings.TextKeys.CALL), buttonStyle)
        raiseButton = TextButton(Settings.getText(Settings.TextKeys.RAISE), buttonStyle)
        chatButton.isTransform = true
        infoButton.isTransform = true
        foldButton.isTransform = true
        callButton.isTransform = true
        raiseButton.isTransform = true
        stage.addActor(pokerTable)
        stage.addActor(pot)
        cards.forEach { stage.addActor(it) }
        stage.addActor(chatButton)
        stage.addActor(infoButton)
        stage.addActor(foldButton)
        stage.addActor(callButton)
        stage.addActor(raiseButton)
        stage.addActor(exitButton)
    }

    override fun update() {
        chatButton.setText(Settings.getText(Settings.TextKeys.CHAT))
        infoButton.setText(Settings.getText(Settings.TextKeys.INFO))
        foldButton.setText(Settings.getText(Settings.TextKeys.FOLD))
        callButton.setText(Settings.getText(Settings.TextKeys.CALL))
        raiseButton.setText(Settings.getText(Settings.TextKeys.RAISE))
        pot.update()
        seats.forEach { it.update() }
    }

    override fun render(delta: Float) {
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
        seats[localSeat-1].playerView.playerName = Settings.getText(Settings.TextKeys.EMPTY_SEAT)
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
