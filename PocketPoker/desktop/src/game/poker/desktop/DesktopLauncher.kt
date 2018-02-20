package game.poker.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import game.poker.PocketPoker

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.title = "PocketPoker"
        config.height = 640
        config.width = 480
        LwjglApplication(PocketPoker(), config)
    }
}
