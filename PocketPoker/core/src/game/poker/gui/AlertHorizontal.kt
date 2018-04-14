package game.poker.gui

import com.badlogic.gdx.scenes.scene2d.Stage

class AlertHorizontal(stage: Stage): AlertBase(stage) {

    init {
        dialog.rotation = -90f
        dialog.setKeepWithinStage(false)
    }

    override fun show(text: String) {
        super.show(text)
        dialog.setPosition(Math.round((stage.width - dialog.height) / 2f).toFloat(),
                Math.round((stage.height - dialog.width) / 2f + dialog.width).toFloat());
    }

}