package game.poker.gui

import com.badlogic.gdx.scenes.scene2d.Stage

class AlertVertical(stage: Stage): AlertBase(stage) {

    override fun show(text: String, action: () -> Unit) {
        super.show(text, action)
        dialog.setPosition(Math.round((stage.width - dialog.width) / 2f).toFloat(),
                Math.round((stage.height - dialog.height) / 2f).toFloat());
    }

}