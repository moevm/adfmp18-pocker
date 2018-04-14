package game.poker.gui

import com.badlogic.gdx.scenes.scene2d.Stage

class AlertVertical(stage: Stage): AlertBase(stage) {

    override fun show(text: String) {
        super.show(text)
        dialog.setPosition(Math.round((stage.width - dialog.width) / 2f).toFloat(),
                Math.round((stage.height - dialog.height) / 2f).toFloat());
    }

}