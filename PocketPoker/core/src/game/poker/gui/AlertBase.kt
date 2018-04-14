package game.poker.gui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.utils.Align
import game.poker.staticFiles.*
import game.poker.staticFiles.Texts.TextKeys


open class AlertBase(val stage: Stage) {

    protected val dialog: Dialog
    protected val textLabel: Label
    protected val PADDING = 10f
    protected var closingAction = { }
    protected val okButton: TextButton

    init {

        val buttonSprite = SpriteDrawable(Sprite(Textures.menuButton))
        val buttonDownSprite = SpriteDrawable(Sprite(Textures.menuButtonDown))
        val buttonStyle = TextButton.TextButtonStyle(buttonSprite, buttonDownSprite, buttonSprite,
                Fonts.mainMenuButtonFont)
        val labelStyle = Label.LabelStyle(Fonts.mainMenuButtonFont, Color.BLACK)

        val style = Window.WindowStyle(Fonts.mainMenuLabelFont, Color.BLACK, SpriteDrawable(Sprite(Textures.labelBg)))
        dialog = Dialog("", style)
        dialog.pad(PADDING)
        dialog.isMovable = false

        textLabel = Label("", labelStyle)
        textLabel.setWrap(true)
        textLabel.setAlignment(Align.center)
        dialog.contentTable.add(textLabel).pad(PADDING).width(800f).row()

        okButton = TextButton(Texts[TextKeys.OK], buttonStyle)
        dialog.contentTable.add(okButton).pad(PADDING)//.width(250f).fillX()

        okButton.addListener(object: ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                dialog.remove()
                closingAction()
            }
        })

    }

    open fun show(text: String, action: () -> Unit) {
        textLabel.setText(text)
        dialog.pack()
        stage.addActor(dialog)
        closingAction = action
        okButton.setText(Texts[TextKeys.OK])
    }

}