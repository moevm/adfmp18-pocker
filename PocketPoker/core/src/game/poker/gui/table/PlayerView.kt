package game.poker.gui.table

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.utils.Align
import game.poker.staticFiles.Fonts
import game.poker.staticFiles.Textures


class PlayerView : Label("",Label.LabelStyle(Fonts.gameLabelFont, Color.BLACK)) {
    private val maxLineLength = 13
    var money = "9 999"
        set(value) {
            field = value
            setText(playerName + "\n" + money + "\n"  + info)
        }

    var playerName: String = "Name"
        set(value) {
            if(value.length >= maxLineLength) {
                field = value.substring(0, maxLineLength - 3) + "..."
            }
            else{
                field = value
            }
            setText(playerName + "\n" + money + "\n"  + info)
        }

    var info: String = ""
        set(value) {
            if(value.length >= maxLineLength) {
                field = value.substring(0, maxLineLength - 3) + "..."
            }
            else{
                field = value
            }
            setText(playerName + "\n" + money + "\n"  + info)
        }

    var needUpdateBackground = false
        private set

    var isDisabled = false // gray background - playerView in game but disconnected
        set(value) {
            field = value
            needUpdateBackground = true
        }

    var isActive = false // yellow background - playerView is thinking
        set(value) {
            field = value
            needUpdateBackground = true
        }

    fun updateBackground(){
        if (!isDisabled) {
            if (isActive){
                style.background = SpriteDrawable(Sprite(Textures.labelBgActive))
            }
            else {
                style.background = SpriteDrawable(Sprite(Textures.labelBg))
            }
        }
        else {
            style.background = SpriteDrawable(Sprite(Textures.labelBgDisabled))
        }
        needUpdateBackground = false
    }

    init {
        style.background = SpriteDrawable(Sprite(Textures.labelBg))
        setSize(250f, 150f)
        setAlignment(Align.center)
        setText(playerName + "\n" + money + "\n"  + info)
    }
}
