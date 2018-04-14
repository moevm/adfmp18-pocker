package game.poker.staticFiles

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator

object Fonts {
    private fun generateMenuButtonFont():BitmapFont {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.characters = FONT_CHARS
        parameter.size = 70
        parameter.color = Color.BLACK
        parameter.minFilter = Texture.TextureFilter.Linear
        parameter.magFilter = Texture.TextureFilter.Linear
        return generator.generateFont(parameter)
    }
    private fun generateMenuLogoFont():BitmapFont {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/Esteban-Regular.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.characters = FONT_CHARS
        parameter.size = 150
        parameter.color = Color.BLACK
        parameter.minFilter = Texture.TextureFilter.Linear
        parameter.magFilter = Texture.TextureFilter.Linear
        return generator.generateFont(parameter)
    }
    private fun generateContainerItemFont():BitmapFont {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.characters = FONT_CHARS
        parameter.size = 50
        parameter.color = Color.BLACK
        parameter.minFilter = Texture.TextureFilter.Linear
        parameter.magFilter = Texture.TextureFilter.Linear
        return generator.generateFont(parameter)
    }
    private fun generateGameLabelFont():BitmapFont {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.characters = FONT_CHARS
        parameter.size = 40
        parameter.color = Color.BLACK
        parameter.minFilter = Texture.TextureFilter.Linear
        parameter.magFilter = Texture.TextureFilter.Linear
        return generator.generateFont(parameter)
    }
    private val FONT_CHARS = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>\u2009"
    val mainMenuButtonFont: BitmapFont by lazy { generateMenuButtonFont() }
    val mainMenuLogoFont: BitmapFont by lazy { generateMenuLogoFont() }
    val mainMenuLabelFont: BitmapFont by lazy { mainMenuButtonFont }
    val gameLabelFont: BitmapFont by lazy { generateGameLabelFont() }
    val containerItemFont: BitmapFont by lazy { generateContainerItemFont() }
}


