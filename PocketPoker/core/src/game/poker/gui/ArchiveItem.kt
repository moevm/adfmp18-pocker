package game.poker.gui

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align

import game.poker.Settings


class ArchiveItem(id: Int, val title: String, val players: Int, val chips: Int, val hands: Int,
                  val isOpened: Boolean = true) : ContainerItem(id) {

    private val ACTION_IMAGE_SIZE = 150f
    private val IS_OPENED_WIDTH = 300f
    private val LEFT_TABLE_HEIGHT = 200f

    private val playersLabel = Label(Settings.getText(Settings.TextKeys.PLAYERS), labelStyle)
    private val chipsLabel = Label(Settings.getText(Settings.TextKeys.CHIPS), labelStyle)
    private val handsLabel = Label(Settings.getText(Settings.TextKeys.HANDS), labelStyle)
    private val isOpenedLabel: Label


    init {

        val isOpenedKey = if (isOpened) Settings.TextKeys.OPENED else Settings.TextKeys.CLOSED
        isOpenedLabel = Label(Settings.getText(isOpenedKey), labelStyle)

        add(Label(title, labelStyle)).pad(PADDING).expandX().fillX()
        add(isOpenedLabel).pad(PADDING).width(IS_OPENED_WIDTH).fillX().align(Align.right).row()

        val leftTable = Table()
        leftTable.pad(0f)
        leftTable.add(playersLabel).padLeft(PADDING).fill()
        leftTable.add(Label(players.toString(), labelStyle)).padLeft(PADDING * 4).expandX().fill().row()
        leftTable.add(chipsLabel).padLeft(PADDING).fill()
        leftTable.add(Label(chips.toString(), labelStyle)).padLeft(PADDING * 4).expandX().fill().row()
        leftTable.add(handsLabel).padLeft(PADDING).fill()
        leftTable.add(Label(hands.toString(), labelStyle)).padLeft(PADDING * 4).expandX().fill()
        add(leftTable).pad(0f).height(LEFT_TABLE_HEIGHT).expandX().fillX()

        val actionImage = Image(nextSprite)
        actionImage.addListener(clickListener)

        add(actionImage).pad(PADDING).width(ACTION_IMAGE_SIZE).height(ACTION_IMAGE_SIZE).right()
    }

}