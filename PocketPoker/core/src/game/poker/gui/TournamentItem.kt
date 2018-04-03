package game.poker.gui

import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import game.poker.Settings

class TournamentItem(id: Int, val title: String, players: Int, totalPlayers: Int, initialStack: Int,
                     val isOpened: Boolean = true, val isStarted: Boolean = false) : ContainerItem(id) {

    private val ACTION_IMAGE_SIZE = 150f
    private val ICON_SIZE = 50f

    private val imageContainer: Container<Image>
    private val nextImage = Image(nextSprite)
    private val watchImage = Image(watchSprite)

    init {

        val isOpenedImage = Image(if (isOpened) unlockedSprite else lockedSprite)

        add(Label(title, labelStyle)).colspan(5).pad(PADDING).expandX().fillX().left()
        add(isOpenedImage).pad(PADDING).width(ICON_SIZE).height(ICON_SIZE).fillX().right().row()

        add(Image(playersSprite)).width(ICON_SIZE).height(ICON_SIZE).pad(PADDING)
        add(Label(players.toString() + "/" + totalPlayers, labelStyle)).pad(PADDING)
        add(Image(blindsSprite)).width(ICON_SIZE).height(ICON_SIZE).pad(PADDING)
        add(Label(initialStack.toString(), labelStyle)).pad(PADDING)
        add(Label("", labelStyle)).pad(PADDING).expandX().fillX()

        imageContainer = Container<Image>()
        nextImage.addListener(clickListener)
        watchImage.addListener(clickListener)
        imageContainer.actor = if (isStarted) watchImage else nextImage
        add(imageContainer).pad(PADDING).width(ACTION_IMAGE_SIZE).height(ACTION_IMAGE_SIZE).fillX().right()

    }

    fun update() {

    }

}