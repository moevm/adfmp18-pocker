package game.poker.gui.table

import com.badlogic.gdx.scenes.scene2d.Stage

class RaiseDialogVertical(stage: Stage, handler: RaiseDialogHorizontal.RaiseResultHandler):
        RaiseDialogHorizontal(stage, handler) {

    init {
        dialog.rotation = -90f
    }

}