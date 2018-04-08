package game.poker.gui.menu

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table

class ScrollableContainer() {

    var clickHandler: ClickHandler
    private val items = mutableMapOf<Int, ContainerItem>()
    private val table = Table()
    val actor: Actor

    init {
        table.pad(0f)
        table.setFillParent(true)
        table.bottom()
        actor = ScrollPane(table)
        clickHandler = object : ClickHandler() {
            override fun click(itemId: Int, mode: Boolean) {
            }
        }
    }

    fun add(item: ContainerItem) {
        items.put(item.id, item)
        table.add(item).pad(10f).expandX().fillX().row()
        item.clickHandler = clickHandler
    }

    fun remove(id: Int) {
        table.removeActor(items[id])
        items.remove(id)
    }

    fun clear() {
        table.clearChildren()
        items.clear()
    }

    fun get(id: Int): ContainerItem? {
        return items[id]
    }

    open class ClickHandler {

        open fun click(itemId: Int, mode: Boolean) {

        }

    }

}
