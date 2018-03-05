package game.poker.gui

import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import game.poker.Settings

class TournamentItem(id: Int,
                     title: String,
                     val places: Int,
                     chips: Int,
                     smallBlind: Int,
                     bigBlind: Int,
                     ante: Int,
                     isOpened: Boolean = true,
                     var isStarted: Boolean = false) : ContainerItem(id) {

    private val ACTION_IMAGE_SIZE = 150f
    private val IS_OPENED_WIDTH = 300f
    private val LEFT_TABLE_HEIGHT = 200f

    private val playersLabel = Label(Settings.getText(Settings.TextKeys.PLAYERS), labelStyle)
    private val chipsLabel = Label(Settings.getText(Settings.TextKeys.CHIPS), labelStyle)
    private val blindsLabel = Label(Settings.getText(Settings.TextKeys.BLINDS), labelStyle)
    private val anteLabel = Label(Settings.getText(Settings.TextKeys.ANTE), labelStyle)
    private val playersValueLabel: Label = Label("0/" + places.toString(), labelStyle)
    private val isOpenedLabel: Label
    private val imageContainer: Container<Image>
    private val nextImage = Image(nextSprite)
    private val watchImage = Image(watchSprite)
    var players = 0

    init {

        val isOpenedKey = if (isOpened) Settings.TextKeys.OPENED else Settings.TextKeys.CLOSED
        isOpenedLabel = Label(Settings.getText(isOpenedKey), labelStyle)

        add(Label(title, labelStyle)).padBottom(2 * PADDING).expandX().fillX()
        add(isOpenedLabel).pad(PADDING).width(IS_OPENED_WIDTH).fillX().align(Align.right).padLeft(100f).row()

        val leftTable = Table()
        leftTable.pad(0f)
        leftTable.add(playersLabel).padLeft(PADDING).fill()
        leftTable.add(playersValueLabel).padLeft(PADDING * 4).expandX().fill().row()
        leftTable.add(chipsLabel).padLeft(PADDING).fill()
        leftTable.add(Label(chips.toString(), labelStyle)).padLeft(PADDING * 4).expandX().fill().row()
        leftTable.add(blindsLabel).padLeft(PADDING).fill()
        leftTable.add(Label(smallBlind.toString() + "/" + bigBlind.toString(), labelStyle))
                .padLeft(PADDING * 4).expandX().fill().row()
        leftTable.add(anteLabel).padLeft(PADDING).fill()
        leftTable.add(Label(ante.toString(), labelStyle)).padLeft(PADDING * 4).expandX().fill().row()
        add(leftTable).pad(0f).height(LEFT_TABLE_HEIGHT).padBottom(2 * PADDING).expandX().fillX()

        imageContainer = Container<Image>()
        nextImage.addListener(clickListener)
        watchImage.addListener(clickListener)
        imageContainer.actor = if (isStarted) watchImage else nextImage
        add(imageContainer).pad(PADDING).width(ACTION_IMAGE_SIZE).height(ACTION_IMAGE_SIZE).right()

    }

    fun update() {
        playersValueLabel.setText(players.toString() + "/" + places.toString())
        imageContainer.actor = if (isStarted) watchImage else nextImage
    }

}