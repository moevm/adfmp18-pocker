package game.poker

import android.os.Bundle
import android.view.View

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        window.decorView.systemUiVisibility = flags
        window.decorView.setOnSystemUiVisibilityChangeListener {
            window.decorView.systemUiVisibility = flags
        }
        val config = AndroidApplicationConfiguration()
        initialize(PocketPoker(), config)
    }

    override fun onDestroy() {
        android.os.Process.killProcess(android.os.Process.myPid())
        super.onDestroy()
    }
}
