package game.poker.core

import game.poker.screens.TableScreen
import game.poker.staticFiles.Texts
import game.poker.staticFiles.Texts.TextKeys


class InfoCreator(val table: TableScreen) {

    fun basic(text: String){
        table.currView.showAlert(text)
    }

    fun withExit(text: String){
        table.currView.showLastAlert(text)
    }

    fun startedGame(){
        basic(Texts[TextKeys.GAME_STARTED])
    }

    fun watchingTable(tableNumber: String, isFinal: Boolean){
        if(isFinal){
            basic("${Texts[TextKeys.WATCHING]} ${Texts[TextKeys.FINAL_TABLE]}")
        }
        else{
            basic("${Texts[TextKeys.WATCHING]} ${Texts[TextKeys.TABLE]} #$tableNumber")
        }
    }

    fun blindsIncreasing(bb: String, sb: String, ante: String){
        val info: String
        if(ante != "0"){
            info = "${Texts[TextKeys.BLINDS]} $bb / $sb ${Texts[TextKeys.ANTE]} $ante"
        }
        else{
            info = "${Texts[TextKeys.BLINDS]} $bb / $sb"
        }
        basic(info)
    }

    fun resit(tableNum: String, isFinal: Boolean){
        if(isFinal){
            basic("${Texts[TextKeys.WAS_RESIT]} ${Texts[TextKeys.FINAL_TABLE]}")
        }
        else{
            basic("${Texts[TextKeys.WAS_RESIT]} ${Texts[TextKeys.TABLE]} #$tableNum")
        }
    }

    fun busted(place: Int){
        withExit("${Texts[TextKeys.YOU_FINISHED]} $place ${Texts[TextKeys.PLACE]}")
    }

    fun win(){
        withExit(Texts[TextKeys.WINNER])
    }

    fun kick(){
        withExit(Texts[TextKeys.KICKED])
    }

    fun finish(msg: String){
        withExit(msg)
    }

    fun successReconnection(){
        basic(Texts[TextKeys.SUCCESS_RECONNECTION])
    }

    fun waitWhileAllPlayersRegister(){
        basic(Texts[TextKeys.WAIT_REGISTER])
    }
}