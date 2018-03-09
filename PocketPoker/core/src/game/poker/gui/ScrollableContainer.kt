package game.poker.gui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table

class ScrollableContainer(private val clickHandler: ClickHandler) {

    private val items = mutableMapOf<Int, ContainerItem>()
    private val table = Table()
    val actor: Actor

    init {
        table.pad(0f)
        table.setFillParent(true)
        table.bottom()
        actor = ScrollPane(table)
    }

    fun add(item: ContainerItem) {
        items[item.id] = item
        table.add(item).pad(10f).fillX().expandX().row()
        item.clickHandler = clickHandler
    }

    fun remove(id: Int) {
        table.removeActor(items[id])
        items.remove(id)
    }

    fun clear() {
        items.clear()
    }

    open class ClickHandler {

        open fun click(itemId: Int) {

        }

    }

}
