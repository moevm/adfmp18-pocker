package game.poker.gui.table

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.utils.Align
import game.poker.Settings
import game.poker.staticFiles.Fonts
import game.poker.staticFiles.Textures
import kotlin.math.roundToInt

open class RaiseDialogBase(val stage: Stage, val handler: RaiseResultHandler) {

    protected val dialog: Dialog
    private val raiseLabel: Label
    private val raiseSlider: Slider
    private val PADDING = 10f
    private var minRaise = 0
    private var maxRaise = 0
    private var currRaise = 0
    private var raiseStep = 0
    private var pot = 0

    init {

        val sliderSprite = Sprite(Textures.sliderKnob)
        sliderSprite.setSize(50f,100f)
        val sliderStyle = Slider.SliderStyle(SpriteDrawable(Sprite(Textures.sliderBg)),
                SpriteDrawable(sliderSprite))
        val buttonSprite = SpriteDrawable(Sprite(Textures.menuButton))
        val buttonDownSprite = SpriteDrawable(Sprite(Textures.menuButtonDown))
        val buttonStyle = TextButton.TextButtonStyle(buttonSprite, buttonDownSprite, buttonSprite,
                Fonts.mainMenuButtonFont)
        val labelStyle = Label.LabelStyle(Fonts.mainMenuButtonFont, Color.BLACK)

        val style = Window.WindowStyle(Fonts.mainMenuLabelFont, Color.BLACK, SpriteDrawable(Sprite(Textures.labelBg)))
        dialog = Dialog("", style)
        dialog.pad(PADDING)
        dialog.isMovable = false
        dialog.setSize(680f, 400f)
        val plusButton = TextButton("+", buttonStyle)
        dialog.contentTable.add(plusButton).pad(PADDING).width(100f)
        val minusButton = TextButton("-", buttonStyle)
        dialog.contentTable.add(minusButton).pad(PADDING).width(100f)
        val potButton = TextButton(Settings.getText(Settings.TextKeys.POT), buttonStyle)
        dialog.contentTable.add(potButton).pad(PADDING).width(250f)
        val closeButton = Image(SpriteDrawable(Sprite(Textures.exitButton)))
        dialog.contentTable.add(closeButton).pad(PADDING).width(100f).height(100f).row()
        raiseSlider = Slider(0f, 100f, 1f, false, sliderStyle)
        dialog.contentTable.add(raiseSlider).colspan(4).pad(PADDING).expandX().fillX().row()
        raiseLabel = Label("", labelStyle)
        raiseLabel.setAlignment(Align.center)
        dialog.contentTable.add(raiseLabel).pad(PADDING).colspan(2).expandX().fillX().center()
        val okButton = TextButton("OK", buttonStyle)
        dialog.contentTable.add(okButton).pad(PADDING).colspan(2).width(250f).fillX().row()

        plusButton.addListener(object: ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                currRaise += raiseStep
                if (currRaise > maxRaise) currRaise = maxRaise
                raiseLabel.setText((currRaise).toString())
                raiseSlider.value = (currRaise - minRaise) * 100f / (maxRaise - minRaise)
            }
        })

        minusButton.addListener(object: ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                currRaise -= raiseStep
                if (currRaise < minRaise) currRaise = minRaise
                raiseLabel.setText((currRaise).toString())
                raiseSlider.value = (currRaise - minRaise) * 100f / (maxRaise - minRaise)
            }
        })

        potButton.addListener(object: ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                currRaise = pot
                raiseLabel.setText((pot).toString())
                raiseSlider.value = (pot - minRaise) * 100f / (maxRaise - minRaise)
            }
        })

        closeButton.addListener(object: ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                //dialog.hide()
                dialog.remove()
            }
        })

        okButton.addListener(object: ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                handler.handle(currRaise)
                //dialog.hide()
                dialog.remove()
            }
        })

        raiseSlider.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                currRaise = minRaise + (raiseSlider.value * (maxRaise - minRaise) / 100f).roundToInt()
                raiseLabel.setText(currRaise.toString())
            }
        })

    }

    fun show(minRaise: Int, maxRaise: Int, raiseStep: Int, pot: Int) {
        this.maxRaise = maxRaise
        this.minRaise = minRaise
        this.raiseStep = raiseStep
        this.pot = pot
        raiseLabel.setText(minRaise.toString())
        raiseSlider.value = 0f
        stage.addActor(dialog)
        //dialog.show(stage)
    }

    open class RaiseResultHandler {
        open fun handle(raiseValue: Int) {}
    }

}