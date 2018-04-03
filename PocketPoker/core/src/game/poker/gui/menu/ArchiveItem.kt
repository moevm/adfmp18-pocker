package game.poker.gui.menu

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table


class ArchiveItem(id: Int, val title: String, val date: String, val tables: Int, val players: Int, val hands: Int,
                  val isOpened: Boolean = true) : ContainerItem(id) {

    private val ACTION_IMAGE_SIZE = 150f

    init {

        val topTable = Table()
        topTable.pad(0f)
        topTable.add(Label(title, labelStyle)).pad(PADDING).expandX().left()
        topTable.add(Label(date, labelStyle)).pad(PADDING)
        val isOpenedTexture = if (isOpened) unlockedSprite else lockedSprite
        topTable.add(Image(isOpenedTexture)).width(ICON_WIDTH).height(ICON_HEIGHT)
        add(topTable).expandX().fillX().row()

        val bottomTable = Table()
        bottomTable.pad(0f)
        bottomTable.add(Image(tablesSprite)).width(ICON_WIDTH).height(ICON_HEIGHT).left()
        bottomTable.add(Label(tables.toString(), labelStyle)).padLeft(PADDING).left()
        bottomTable.add(Image(playersSprite)).width(ICON_WIDTH).height(ICON_HEIGHT).padLeft(PADDING).left()
        bottomTable.add(Label(players.toString(), labelStyle)).padLeft(PADDING).left()
        bottomTable.add(Image(handsSprite)).width(ICON_WIDTH).height(ICON_HEIGHT).padLeft(PADDING).left()
        bottomTable.add(Label(hands.toString(), labelStyle)).padLeft(PADDING).left()
        val actionImage = Image(nextSprite)
        actionImage.addListener(clickListener)
        bottomTable.add(actionImage).pad(PADDING).width(ACTION_IMAGE_SIZE).height(ACTION_IMAGE_SIZE).expandX().right()
        add(bottomTable).expandX().fillX()

    }

}