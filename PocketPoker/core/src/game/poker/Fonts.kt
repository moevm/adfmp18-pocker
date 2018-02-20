package game.poker

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter

object Fonts {
    fun generateMenuButtonFont():BitmapFont {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("arial/arial.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.characters = FONT_CHARS
        parameter.size = 50
        parameter.color = Color.BLACK
        return generator.generateFont(parameter)
    }
    val FONT_CHARS = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>"
    val mainMenuButtonFont = generateMenuButtonFont() //generator.generateFont(genFontParam())
}


