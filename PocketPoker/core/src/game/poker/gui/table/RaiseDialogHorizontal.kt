package game.poker.gui.table

import com.badlogic.gdx.scenes.scene2d.Stage


class RaiseDialogHorizontal(stage: Stage, handler: RaiseDialogBase.RaiseResultHandler):
        RaiseDialogBase(stage, handler) {

    init {
        dialog.setPosition(350f, 1250f)
        dialog.rotation = -90f
    }

}