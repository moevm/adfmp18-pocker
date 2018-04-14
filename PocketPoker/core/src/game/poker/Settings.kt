package game.poker

import com.badlogic.gdx.math.Interpolation
import game.poker.staticFiles.Texts

object Settings{

    enum class CardsType{ COLOR_4, COLOR_2 }

    enum class TableOrientation { VERTICAL, HORIZONTAL, BY_GYRO}

    enum class TableMode { Game, Spectate, Replay }

    var currLang = Texts.Langs.RUS
    var currCards = CardsType.COLOR_2

    var nick = ""
    var token = ""
    var currTableId = 0
    var currArchiveTournamentId = 0
    var currTournamentId = 0
    var currOrientation = TableOrientation.VERTICAL
    var currTableMode = TableMode.Game
    var soundVolume = 0.5f
    var isOnlyOneTable = false

    val animationDuration = 0.5f
    val animationInterpolation: Interpolation = Interpolation.exp5
}