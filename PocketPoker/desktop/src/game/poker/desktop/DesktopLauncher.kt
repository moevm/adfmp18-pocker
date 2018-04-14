package game.poker.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import game.poker.PocketPoker

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.title = "PocketPoker"
        config.forceExit = false
        // so height/width == 1920/1080
        config.height = 640//950
        config.width = 360//534
        LwjglApplication(PocketPoker(), config)
    }
}
