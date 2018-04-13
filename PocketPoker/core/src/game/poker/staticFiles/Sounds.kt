package game.poker.staticFiles

import com.badlogic.gdx.Gdx
import game.poker.Settings
import java.util.*

object Sounds {

    enum class SoundType{
        ATTENTION, CHECK, COLLECT, FOLD, GRAB, CHIPS
    }

    private val alert = Gdx.audio.newSound(Gdx.files.internal("sounds/alert.mp3"))
    private val check = Gdx.audio.newSound(Gdx.files.internal("sounds/check.mp3"))
    private val collect = Gdx.audio.newSound(Gdx.files.internal("sounds/collect.mp3"))
    private val fold = Gdx.audio.newSound(Gdx.files.internal("sounds/fold.mp3"))
    private val grab = Gdx.audio.newSound(Gdx.files.internal("sounds/grab.mp3"))
    private val chip1 = Gdx.audio.newSound(Gdx.files.internal("sounds/chip1.mp3"))
    private val chip2 = Gdx.audio.newSound(Gdx.files.internal("sounds/chip2.mp3"))
    private val chip3 = Gdx.audio.newSound(Gdx.files.internal("sounds/chip3.mp3"))
    private val chip4 = Gdx.audio.newSound(Gdx.files.internal("sounds/chip4.mp3"))
    private val chip5 = Gdx.audio.newSound(Gdx.files.internal("sounds/chip5.mp3"))
    private val chip = arrayOf(chip1, chip2, chip3, chip4, chip5)

    private val random = Random()

    fun play(type: SoundType){
        when(type){
            SoundType.ATTENTION -> alert.play(Settings.soundVolume)
            SoundType.CHECK -> check.play(Settings.soundVolume)
            SoundType.COLLECT -> collect.play(Settings.soundVolume)
            SoundType.FOLD -> fold.play(Settings.soundVolume)
            SoundType.GRAB -> grab.play(Settings.soundVolume)
            SoundType.CHIPS -> chip[random.nextInt(chip.size)].play(Settings.soundVolume)
        }
    }

}