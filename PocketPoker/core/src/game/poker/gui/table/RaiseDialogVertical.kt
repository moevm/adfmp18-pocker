package game.poker.gui.table

import com.badlogic.gdx.scenes.scene2d.Stage

class RaiseDialogVertical(stage: Stage, handler: RaiseDialogBase.RaiseResultHandler):
        RaiseDialogBase(stage, handler) {

    init {
        dialog.setPosition(200f, 800f)
    }

}