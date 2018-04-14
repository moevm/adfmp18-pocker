package game.poker.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.List
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.scenes.scene2d.ui.Slider
import com.google.gson.JsonObject

import game.poker.PocketPoker
import game.poker.Settings
import game.poker.staticFiles.*
import game.poker.staticFiles.Texts.TextKeys


class SettingsMenu(val game: PocketPoker) : BaseScreen {

    private val stage = Stage(game.view)
    private val PADDING = 50f
    private val languageSelect: SelectBox<String>
    private val soundLabel: Label
    private val musicLabel: Label
    private val languageLabel: Label
    private val cardsLabel: Label
    private val mainMenuButton: TextButton
    private val cardSelect: SelectBox<String>
    private val orientLabel: Label
    private val orientSelect: SelectBox<String>

    init {

        stage.addActor(Image(Textures.menuBg))

        val buttonSprite = SpriteDrawable(Sprite(Textures.menuButton))
        val buttonDownSprite = SpriteDrawable(Sprite(Textures.menuButtonDown))
        val selectBoxSprite = SpriteDrawable(Sprite(Textures.scroll))
        val sliderSprite = Sprite(Textures.sliderKnob)
        sliderSprite.setSize(50f,100f)

        val labelStyle = LabelStyle(Fonts.mainMenuLabelFont, Color.BLACK)
        val buttonStyle = TextButtonStyle(buttonSprite, buttonDownSprite, buttonSprite,
                Fonts.mainMenuButtonFont)
        val sliderStyle = Slider.SliderStyle(SpriteDrawable(Sprite(Textures.sliderBg)),
                SpriteDrawable(sliderSprite))
        val selectBoxStyle = SelectBox.SelectBoxStyle(Fonts.mainMenuLabelFont,
                Color.BLACK,
                selectBoxSprite,
                ScrollPane.ScrollPaneStyle(SpriteDrawable(Sprite(Textures.scrollBg)),
                        SpriteDrawable(Sprite(Textures.hScroll)),
                        SpriteDrawable(Sprite(Textures.hScrollKnob)),
                        SpriteDrawable(Sprite(Textures.vScroll)),
                        SpriteDrawable(Sprite(Textures.vScrollKnob))),
                List.ListStyle(Fonts.mainMenuLabelFont,
                        Color.BLACK,
                        Color.GRAY,
                        SpriteDrawable(Sprite(Textures.list_selection)))
        )

        val table = Table()
        table.pad(PADDING)
        table.setFillParent(true)
        table.align(Align.bottom)

        soundLabel = Label(Texts[TextKeys.SOUND_LEVEL], labelStyle)
        table.add(soundLabel).colspan(2).pad(PADDING).row()

        val soundScroll = Slider(0f, 1f, 0.01f, false, sliderStyle)
        soundScroll.value = Settings.soundVolume
        soundScroll.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                println("sound " + soundScroll.value)
                Settings.soundVolume = soundScroll.value
            }
        })
        table.add(soundScroll).colspan(2).pad(PADDING).fill().width(game.gameWidth * 0.8f).row()

        musicLabel = Label(Texts[TextKeys.MUSIC_LEVEL], labelStyle)
        table.add(musicLabel).colspan(2).pad(PADDING).row()
        musicLabel.isVisible = false //reason: not used

        val musicScroll = Slider(0f, 1f, 0.01f, false, sliderStyle)
        table.add(musicScroll).colspan(2).pad(PADDING).width(game.gameWidth * 0.8f).fill().row()
        musicScroll.isVisible = false //reason: not used

        languageLabel = Label(Texts[TextKeys.LANGUAGE], labelStyle)
        table.add(languageLabel).pad(PADDING).width(game.gameWidth * 0.25f)

        languageSelect = SelectBox<String>(selectBoxStyle)
        languageSelect.setItems(Texts[TextKeys.LANG_RUS],
                Texts[TextKeys.LANG_ENG])
        languageSelect.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                val newLang = when(languageSelect.selectedIndex){
                    0 -> Texts.Langs.RUS
                    1 -> Texts.Langs.ENG
                    else -> throw IllegalArgumentException("Bad index at languageSelect")
                }
                if(newLang != Settings.currLang){
                    Settings.currLang = newLang
                    game.updateLang()
                }
            }
        })
        languageSelect.setAlignment(Align.center)
        table.add(languageSelect).pad(PADDING).width(game.gameWidth * 0.55f).row()

        cardsLabel = Label(Texts[TextKeys.CARDS], labelStyle)
        table.add(cardsLabel).pad(PADDING).width(game.gameWidth * 0.25f)

        cardSelect = SelectBox<String>(selectBoxStyle)
        cardSelect.setItems(Texts[TextKeys.CARD_2_COLOR],
                Texts[TextKeys.CARD_4_COLOR])
        cardSelect.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                when(cardSelect.selectedIndex){
                    0 -> Settings.currCards = Settings.CardsType.COLOR_2
                    1 -> Settings.currCards = Settings.CardsType.COLOR_4
                    else -> throw IllegalArgumentException("Bad index at orientSelect")
                }
            }
        })
        cardSelect.setAlignment(Align.center)
        table.add(cardSelect).pad(PADDING).width(game.gameWidth * 0.55f).row()

        orientLabel = Label(Texts[TextKeys.ORIENTATION],labelStyle)
        table.add(orientLabel).pad(PADDING).colspan(2).row()

        orientSelect = SelectBox<String>(selectBoxStyle)
        orientSelect.setItems(Texts[TextKeys.ORIENT_VERT],
                Texts[TextKeys.ORIENT_HOR],
                Texts[TextKeys.ORIENT_GYRO])
        orientSelect.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                when(orientSelect.selectedIndex){
                    0 -> Settings.currOrientation = Settings.TableOrientation.VERTICAL
                    1 -> Settings.currOrientation = Settings.TableOrientation.HORIZONTAL
                    2 -> Settings.currOrientation = Settings.TableOrientation.BY_GYRO
                    else -> throw IllegalArgumentException("Bad index at orientSelect")
                }
            }
        })
        orientSelect.setAlignment(Align.center)
        table.add(orientSelect).pad(PADDING).width(game.gameWidth * 0.55f).colspan(2).row()

        mainMenuButton = TextButton(Texts[TextKeys.MAIN_MENU], buttonStyle)
        mainMenuButton.addListener(game.switches[ScreenType.MAIN_MENU])
        table.add(mainMenuButton).colspan(2).pad(PADDING).padTop(100f).
                padRight(game.gameWidth * 0.3f).fill().height(100f)
        stage.addActor(table)
    }

    override fun update(){
        soundLabel.setText(Texts[TextKeys.SOUND_LEVEL])
        musicLabel.setText(Texts[TextKeys.MUSIC_LEVEL])
        languageLabel.setText(Texts[TextKeys.LANGUAGE])
        cardsLabel.setText(Texts[TextKeys.CARDS])
        mainMenuButton.setText(Texts[TextKeys.MAIN_MENU])
        orientLabel.setText(Texts[TextKeys.ORIENTATION])
        val currCardColor = cardSelect.selectedIndex
        val currLang = languageSelect.selectedIndex
        val currOrient = orientSelect.selectedIndex
        cardSelect.setItems(Texts[TextKeys.CARD_2_COLOR],
                Texts[TextKeys.CARD_4_COLOR])
        cardSelect.selectedIndex = currCardColor
        languageSelect.setItems(Texts[TextKeys.LANG_RUS],
                Texts[TextKeys.LANG_ENG])
        languageSelect.selectedIndex = currLang
        orientSelect.setItems(Texts[TextKeys.ORIENT_VERT],
                Texts[TextKeys.ORIENT_HOR],
                Texts[TextKeys.ORIENT_GYRO])
        orientSelect.selectedIndex = currOrient
    }

    override fun show(){
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        stage.act()
        stage.draw()
    }

    override fun resize(width: Int, height: Int){

    }

    override fun pause(){

    }

    override fun resume(){

    }

    override fun hide(){

    }

    override fun dispose(){

    }

    override fun receiveFromServer(json: JsonObject) {

    }

    override fun setPreviousScreen() {
        game.setCurrScreen(ScreenType.MAIN_MENU)
    }
}
