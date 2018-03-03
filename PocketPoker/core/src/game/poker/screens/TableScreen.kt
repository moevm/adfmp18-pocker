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
import game.poker.core.Visibility
import game.poker.staticFiles.Textures


class TableScreen(val game: PocketPoker) : BaseScreen {

    private var isLandscape: Boolean = false
    private val stage = Stage(game.view)
    private var seats = Array(9) { i -> Seat(i) }
    private val mainMenuButton: ImageButton

    init {
        mainMenuButton = ImageButton(SpriteDrawable(Sprite(Textures.exitButton)),
                SpriteDrawable(Sprite(Textures.exitButtonDown)))
        mainMenuButton.addListener(game.switches[ScreenType.MAIN_MENU])
        mainMenuButton.x = 10f
        mainMenuButton.y = 1790f
        mainMenuButton.width = 120f
        mainMenuButton.height = 120f
        val tableSprite = Sprite(Textures.pokerTable)
        tableSprite.setSize(game.gameWidth * 0.8f,game.gameHeight * 0.8f)
        tableSprite.rotate90(true)
        val pokerTable = Image(SpriteDrawable(tableSprite))
        pokerTable.x = game.gameWidth * 0.1f
        pokerTable.y = game.gameHeight * 0.1f
        stage.addActor(pokerTable)
        seats[0].setCards(Card(Rank.Ace,Suit.Clubs, Visibility.Open),Card(Rank.Jack,Suit.Diamonds, Visibility.Open))
        seats.forEach {
            //it.setCards(Card(Rank.Ace,Suit.Clubs, Visibility.Open),Card(Rank.Jack,Suit.Diamonds, Visibility.Open))
            //it.overturnCards()
            //it.playerView.playerName = "Name1"
            stage.addActor(it)
        }
        stage.addActor(mainMenuButton)
    }

    override fun update(){

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
}