package game.poker.gui.menu

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label


class TournamentTableItem(id: Int, val title: String) : ContainerItem(id) {

    private val ACTION_IMAGE_SIZE = 150f

    init {
        add(Label(title, labelStyle)).pad(PADDING).expandX().fillX().left()
        val actionImage = Image(watchSprite)
        actionImage.addListener(clickListener)
        add(actionImage).pad(PADDING).width(ACTION_IMAGE_SIZE).height(ACTION_IMAGE_SIZE).right()
    }

}