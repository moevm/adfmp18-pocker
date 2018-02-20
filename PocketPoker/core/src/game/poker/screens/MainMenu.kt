package game.poker.screens

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.g2d.Sprite

import game.poker.Textures
import game.poker.PocketPoker
import game.poker.gui.Button


class MainMenu(val game: PocketPoker) : Screen {
    private val background = Sprite(Textures.mainMenuBg)
    private val easyGame = Button(200f, 700f, 700f, 150f, "Quick game")

    init {
        background.setBounds(0f, 0f, game.gameWidth, game.gameHeight)
    }

    override fun show(){
        println("MainMenu show")
    }

    override fun render(delta: Float){
        background.draw(game.batch)
        easyGame.draw(game.batch)
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