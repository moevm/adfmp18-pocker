package game.poker

object Settings{

    enum class Langs{ RUS, ENG }
    enum class TextKeys{
        POKCET_POKER,
        QUICK_GAME,
        TOURNAMENT,
        ARCHIVE,
        SETTINGS,
        EXIT
    }

    private fun generateLanguages() : Map<Pair<Langs, TextKeys>, String>{
        val map = mutableMapOf<Pair<Langs, TextKeys>, String>()

        map[Pair(Langs.RUS, TextKeys.POKCET_POKER)] = "PocketPoker"
        map[Pair(Langs.ENG, TextKeys.POKCET_POKER)] = "PocketPoker"

        map[Pair(Langs.RUS, TextKeys.QUICK_GAME)] = "Быстрая игра"
        map[Pair(Langs.ENG, TextKeys.QUICK_GAME)] = "Quick game"

        map[Pair(Langs.RUS, TextKeys.TOURNAMENT)] = "Турнир"
        map[Pair(Langs.ENG, TextKeys.TOURNAMENT)] = "Tournament"

        map[Pair(Langs.RUS, TextKeys.ARCHIVE)] = "Архив"
        map[Pair(Langs.ENG, TextKeys.ARCHIVE)] = "Archive"

        map[Pair(Langs.RUS, TextKeys.SETTINGS)] = "Настройки"
        map[Pair(Langs.ENG, TextKeys.SETTINGS)] = "Settings"

        map[Pair(Langs.RUS, TextKeys.EXIT)] = "Выход"
        map[Pair(Langs.ENG, TextKeys.EXIT)] = "Exit"

        return map
    }

    private val langMap = generateLanguages()
    var currLang = Langs.RUS

    fun getText(key: TextKeys) = langMap[Pair(currLang, key)]
}