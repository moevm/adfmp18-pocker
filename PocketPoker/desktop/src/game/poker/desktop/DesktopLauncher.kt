package game.poker.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import game.poker.PocketPoker

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.title = "PocketPoker"
        // so height/width == 1920/1080
        config.height = 576
        config.width = 324
        LwjglApplication(PocketPoker(), config)
    }
}
