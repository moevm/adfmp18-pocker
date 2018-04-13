package game.poker.gui.table

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.utils.Align
import game.poker.Settings
import game.poker.staticFiles.*
import game.poker.staticFiles.Texts.TextKeys

class Pot : Group(){
    var money = 0L
        set(value) {
            field = value
            chipstack.setChips(money)
        }
    var count = "0"
        set(value) {
            field = value
            label.setText(Texts[TextKeys.POT] + ":\n" + count)
        }
    val label = Label(Texts[TextKeys.POT] + ":\n" + count,
                        Label.LabelStyle(Fonts.gameLabelFont, Color.BLACK))
    val chipstack = Chipstack()

    init {
        label.style.background = SpriteDrawable(Sprite(Textures.labelBg))
        label.setSize(240f, 100f)
        label.setAlignment(Align.center)
        chipstack.setChips(money)
        addActor(label)
        addActor(chipstack)
    }

    fun update(){
        label.setText(Texts[TextKeys.POT] + ":\n" + money)
    }

    fun setMoneyAfterAnimation(newMoney: Long) {
        val resetChips = object: Action(){
            var curTime = 0f
            var duration = Settings.animationDuration
            var complete = false
            override fun act(delta: Float): Boolean {
                if (complete) return true
                curTime += delta
                if (curTime >= duration){
                    chipstack.setChips(newMoney)
                    chipstack.updateChips()
                    complete = true
                    return true
                }
                return false
            }
        }
        addAction(resetChips)
    }
}
