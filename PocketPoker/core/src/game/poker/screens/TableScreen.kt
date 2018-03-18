package game.poker.screens

import com.google.gson.JsonObject
import game.poker.PocketPoker
import game.poker.Settings
import game.poker.gui.table.TableViewBase
import game.poker.gui.table.TableViewVertical
import game.poker.gui.table.TableViewHorizontal
import game.poker.core.Rank
import game.poker.core.Suit
import game.poker.core.Card
import game.poker.core.Visibility
import game.poker.core.handle.Handler

class TableScreen(val game: PocketPoker) : BaseScreen {

    var orientation = Settings.currOrientation
        private set(value) {
            field = value
            if (value == Settings.TableOrientation.VERTICAL || value == Settings.TableOrientation.BY_GYRO){
                tableViewVertical.fit(currView)
                currView = tableViewVertical
            } else {
                tableViewHorizontal.fit(currView)
                currView = tableViewHorizontal
            }
        }
    var mode = Handler.Mode.Game
        set(value) {
            field = value
            tableViewHorizontal.mode = value
            tableViewVertical.mode = value
        }
    // describes is this table is final or not
    // note: in final table all empty seats are hidden
    //     but in other tables all empty seats just shows with text "Empty seat"
    var isFinal = false
        set(value) {
            field = value
            tableViewVertical.isFinal = value
            tableViewHorizontal.isFinal = value
        }
    private val tableViewVertical = TableViewVertical(game)
    private val tableViewHorizontal = TableViewHorizontal(game)
    var currView : TableViewBase = tableViewVertical
        private set

    init {
        //DEBUG
        //isFinal = true
        currView.setFlopCards(Card(Rank.Ten,Suit.Clubs, Visibility.Open),Card(Rank.Ace,Suit.Spades, Visibility.Open),Card(Rank.Ace,Suit.Clubs, Visibility.Open))
        currView.setTurnCard(Card(Rank.Five,Suit.Clubs, Visibility.Open))
        currView.setRiverCard(Card(Rank.Two,Suit.Clubs, Visibility.Open))
        for (i in 2..9) {
            //currView.setPlayerCards(i, Card(Rank.Ace,Suit.Hearts, Visibility.Open), Card(Rank.Ace,Suit.Diamonds, Visibility.Open))
            currView.updatePlayerInfo(i,"$i","999","call 30")
            currView.setChips(i,309999)
        }
        currView.setEmptyPlayer(4)
        currView.setDealerPos(8)
        currView.dealCards()
        currView.setChips(1,309999)
        currView.updatePlayerInfo(1,"Николай","999","raise 30")
        currView.setPlayerCards(1, Card(Rank.Ace,Suit.Hearts, Visibility.Open), Card(Rank.Ace,Suit.Diamonds, Visibility.Open))
        currView.setPlayerDisconnected(2,true)
        currView.switchDecision(9)
        currView.setPotChips(999999)
        currView.setPotCount("999 999")
        //END DEBUG
    }

    override fun update(){
        tableViewVertical.update()
        tableViewHorizontal.update()
    }

    override fun show(){
        if (orientation != Settings.currOrientation) orientation = Settings.currOrientation
        currView.show()
    }

    override fun render(delta: Float) {
        currView.render(delta)
    }

    override fun resize(width: Int, height: Int){
        currView.resize(width,height)
    }

    override fun pause(){
        currView.pause()
    }

    override fun resume(){
        currView.resume()
    }

    override fun hide(){
        currView.hide()
    }

    override fun dispose(){
        tableViewVertical.dispose()
        tableViewHorizontal.dispose()
    }

    override fun receiveFromServer(json: JsonObject) {

    }
}
