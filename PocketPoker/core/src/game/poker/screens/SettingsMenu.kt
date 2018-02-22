package game.poker.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.List
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.utils.Align

import game.poker.PocketPoker
import game.poker.staticFiles.Textures
import game.poker.staticFiles.Fonts


class SettingsMenu(val game: PocketPoker) : Screen {

    private val stage = Stage(game.view)

    init {

        stage.addActor(Image(Textures.menuBg))

        val selectBoxSprite = SpriteDrawable(Sprite(Textures.scroll))
        val languageSelect = SelectBox<String>(SelectBox.SelectBoxStyle(Fonts.mainMenuLabelFont,
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
                 ))
        val str = arrayOf("First","Second","Third")
        languageSelect.setItems(com.badlogic.gdx.utils.Array<String>(str))
        languageSelect.setAlignment(Align.center)
        languageSelect.width = 500f
        stage.addActor(languageSelect)
        Gdx.input.inputProcessor = stage
    }

    override fun show(){
        println("SettingsMenu show")
    }

    override fun render(delta: Float) {
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
}