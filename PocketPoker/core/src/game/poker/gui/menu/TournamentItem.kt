package game.poker.gui.menu

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label


class TournamentItem(id: Int, val title: String, players: Int, totalPlayers: Int, initialStack: Int,
                     val isOpened: Boolean = true, val isStarted: Boolean = false,
                     val canPlay: Boolean) : ContainerItem(id) {

    private val ACTION_IMAGE_SIZE = 150f
    private val ICON_SIZE = 50f
    private val nextImage = Image(nextSprite)
    private val watchImage = Image(watchSprite)

    init {

        val isOpenedImage = Image(if (isOpened) unlockedSprite else lockedSprite)

        add(Label(title, labelStyle)).colspan(5).pad(PADDING).expandX().fillX().left()
        add(isOpenedImage).colspan(2).pad(PADDING).width(ICON_SIZE).height(ICON_SIZE).fillX().right().row()

        add(Image(playersSprite)).width(ICON_SIZE).height(ICON_SIZE).pad(PADDING)
        add(Label(players.toString() + "/" + totalPlayers, labelStyle)).pad(PADDING)
        add(Image(blindsSprite)).width(ICON_SIZE).height(ICON_SIZE).pad(PADDING)
        add(Label(initialStack.toString(), labelStyle)).pad(PADDING)
        add(Label("", labelStyle)).pad(PADDING).expandX().fillX()

        nextImage.addListener(nextClickListener)
        watchImage.addListener(watchClickListener)

        if (!isStarted) {
            add(nextImage).pad(PADDING).width(ACTION_IMAGE_SIZE).height(ACTION_IMAGE_SIZE).right()
        }
        else {
            if (!canPlay) {
                add(watchImage).pad(PADDING).width(ACTION_IMAGE_SIZE).height(ACTION_IMAGE_SIZE).right()
            }
            else {
                add(nextImage).pad(PADDING).width(ACTION_IMAGE_SIZE).height(ACTION_IMAGE_SIZE).right()
                add(watchImage).pad(PADDING).width(ACTION_IMAGE_SIZE).height(ACTION_IMAGE_SIZE).right()
            }
        }

    }

    fun update() {

    }

}