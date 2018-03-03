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
    private val maxNameLength = 10
    var money = "9 999 999 999"
        set(value) {
            if(value.length >= maxNameLength + 1) {
                field = value.substring(0,maxNameLength) + "..."
            }
            else{
                field = value
            }
            label.setText(playerName + "\n" + money + "\n"  + info)
        }
    var playerName: String = "Name"
        set(value) {
            if(value.length >= maxNameLength + 1) {
                field = value.substring(0,maxNameLength) + "..."
            }
            else{
                field = value
            }
            label.setText(playerName + "\n" + money + "\n"  + info)
        }
    var info: String = ""
        set(value) {
            if(value.length >= maxNameLength + 1) {
                field = value.substring(0,maxNameLength) + "..."
            }
            else{
                field = value
            }
            label.setText(playerName + "\n" + money + "\n"  + info)
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
        when(seat.positionNumber){
            0 -> {
                label.x = x - 350f
                label.y = y
            }
            6,7,8 -> {
                label.x = x - 100f
            }
        }
    }
}
