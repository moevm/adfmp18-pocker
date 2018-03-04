package game.poker.core

import game.poker.core.handle.shortcut


class Premoves(val table: TableScreen) {

    var first = false
    var second = false
    var third = false
    var isVisible = false
    var inTwoChoices = false

    fun hide(){
        if(isVisible){
            isVisible = false
            reset()
            table.hidePremoves()
        }
    }

    fun show(){
        if(!isVisible){
            isVisible = true
            reset()
            table.showPremoves()
        }
    }

    fun checkFold(){
        show()
        table.setPremoveText("Check/Fold", "Check", "Call any")
    }

    fun callFold(money: Long){
        show()
        table.setPremoveText("Fold", "Call " + money.shortcut(), "Call any")
    }

    fun allInFold(money: Long){
        show()
        twoChoicesMode()
        table.setPremoveText("Fold", "Call " + money.shortcut(), "")
    }

    fun twoChoicesMode(){
        if(!inTwoChoices){
            inTwoChoices = true
            if(third){
                set(2, true)
            }
            else if(second){
                set(2, true)
            }
            table.setThirdPremoveHidden(true)
        }
    }

    fun threeChoicesMode(){
        if(inTwoChoices){
            inTwoChoices = false
            table.setThirdPremoveHidden(false)
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
        table.setPremovesChecked(first, second, third)
    }

}