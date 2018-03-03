package game.poker.gui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.utils.Align
import game.poker.staticFiles.Fonts
import game.poker.staticFiles.Textures


class PlayerView(seat:Seat) : Widget() {
    var money = "9 999 999 999"
        set(value) {
            if(value.length >= 13) {
                label.setText(playerName + "\n" + value.substring(0, 12) + "\n" + info)
            }
            else{
                label.setText(playerName + "\n" + value + "\n" + info)
            }
        }
    var playerName: String = "Name"
        set(value) {
            if(value.length >= 13){
                label.setText(value.substring(0,12) + "\n" + money + "\n"  + info)
            }
            else{
                label.setText(value + "\n" + money + "\n"  + info)
            }
        }
    var info: String = ""
        set(value) {
            if(value.length >= 13) {
                label.setText(playerName + "\n" + money + "\n" + value.substring(0, 12))
            }
            else{
                label.setText(playerName + "\n" + money + "\n" + value)
            }
        }

    var isDisabled = false // gray background - playerView in game but disconnected
    var isActive = false // yellow background - playerView is thinking
    private val label = Label(playerName + "\n" + money + "\n"  + info, Label.LabelStyle(Fonts.gameLabelFont, Color.BLACK))
    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        label.draw(batch, parentAlpha)
    }
    init {
        x = seat.x
        y = seat.y
        label.style.background = SpriteDrawable(Sprite(Textures.labelBg))
        label.setSize(250f,150f)
        label.x = x
        label.y = y + 150
        label.setAlignment(Align.center)
        when(seat.i){
            0 -> {
                label.x = x - 350f
                label.y = y
            }
            6 ->{
                label.x = x - 100f
            }
            7->{
                label.x = x - 100f
            }
            8->{
                label.x = x - 100f
            }
        }
    }
}
