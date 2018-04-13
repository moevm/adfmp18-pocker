package game.poker.core

import game.poker.core.handle.shortcut
import game.poker.screens.TableScreen
import game.poker.staticFiles.Texts
import game.poker.staticFiles.Texts.TextKeys

class Premoves(val table: TableScreen) {

    var first = false
    var second = false
    var third = false
    private var isVisible = false
    private var inTwoChoices = false

    fun hide(){
        if(isVisible){
            isVisible = false
            reset()
            table.currView.setPremoves(false)
        }
    }

    fun show(){
        if(!isVisible){
            isVisible = true
            reset()
            table.currView.setPremoves(true)
        }
    }

    fun checkFold(){
        show()
        table.currView.setPremoveText(Texts[TextKeys.CHECK] + "/" +
                Texts[TextKeys.FOLD],
                Texts[TextKeys.CHECK],
                Texts[TextKeys.CALL] + " " +
                        Texts[TextKeys.ANY])
    }

    fun callFold(money: Long){
        show()
        table.currView.setPremoveText(Texts[TextKeys.FOLD],
                Texts[TextKeys.CALL] + " " + money.shortcut(),
                Texts[TextKeys.CALL] + " " +
                        Texts[TextKeys.ANY])
    }

    fun allInFold(money: Long){
        show()
        twoChoicesMode()
        table.currView.setPremoveText(Texts[TextKeys.FOLD],
                Texts[TextKeys.CALL] + " " + money.shortcut(),
                "")
    }

    private fun twoChoicesMode(){
        if(!inTwoChoices){
            inTwoChoices = true
            if(third){
                set(2, true)
            }
            else if(second){
                set(2, true)
            }
            table.currView.setThirdPremoveHidden(true)
        }
    }

    private fun threeChoicesMode(){
        if(inTwoChoices){
            inTwoChoices = false
            table.currView.setThirdPremoveHidden(false)
        }
    }

    fun reset(){
        threeChoicesMode()
        set(0, false)
    }

    fun set(ans: Int, checked: Boolean){
        first = false
        second = false
        third = false
        when(ans) {
            1 -> first = checked
            2 -> second = checked
            3 -> third = checked
        }
        table.currView.setPremovesChecked(first, second, third)
    }

}