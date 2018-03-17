package game.poker.gui

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table


class TableItem(id: Int, val title: String, val hands: Int) : ContainerItem(id) {

    private val ACTION_IMAGE_SIZE = 150f

    init {

        width = 1000f
        val leftTable = Table()
        leftTable.pad(0f)
        leftTable.add(Label(title, labelStyle)).colspan(2).pad(PADDING).expandX().left().row()
        leftTable.add(Image(handsSprite)).width(ICON_WIDTH).height(ICON_HEIGHT).padLeft(PADDING).left()
        leftTable.add(Label(hands.toString(), labelStyle)).padLeft(PADDING).expandX().left()
        add(leftTable).pad(0f).expandX().fillX()

        val actionImage = Image(nextSprite)
        actionImage.addListener(clickListener)
        add(actionImage).pad(PADDING).width(ACTION_IMAGE_SIZE).height(ACTION_IMAGE_SIZE).right()
    }

}