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

    var isDisabled = false // gray background - playerView in game but disconnected
        set(value) {
            field = value
            style.background = if (value){
                SpriteDrawable(Sprite(Textures.labelBgDisabled))
            } else {
                if (isActive) SpriteDrawable(Sprite(Textures.labelBgActive))
                else SpriteDrawable(Sprite(Textures.labelBg))
            }
        }

    var isActive = false // yellow background - playerView is thinking
        set(value) {
            field = value
            if (!isDisabled) {
                style.background = if (value) SpriteDrawable(Sprite(Textures.labelBgActive))
                else SpriteDrawable(Sprite(Textures.labelBg))
            }
        }

    init {
        style.background = SpriteDrawable(Sprite(Textures.labelBg))
        setSize(250f, 150f)
        setAlignment(Align.center)
        setText(playerName + "\n" + money + "\n"  + info)
    }
}
