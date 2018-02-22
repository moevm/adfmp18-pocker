package game.poker

object Settings{

    enum class Langs{ RUS, ENG }
    enum class TextKeys{
        POCKET_POKER,
        NICK,
        QUICK_GAME,
        TOURNAMENT,
        ARCHIVE,
        SETTINGS,
        EXIT,
        SOUND_LEVEL,
        MUSIC_LEVEL,
        LANGUAGE,
        CARDS,
        MAIN_MENU
    }

    private fun generateLanguages() : Map<Pair<Langs, TextKeys>, String>{
        val map = mutableMapOf<Pair<Langs, TextKeys>, String>()

        map[Pair(Langs.RUS, TextKeys.POCKET_POKER)] = "PocketPoker"
        map[Pair(Langs.ENG, TextKeys.POCKET_POKER)] = "PocketPoker"

        map[Pair(Langs.RUS, TextKeys.NICK)] = "Ник"
        map[Pair(Langs.ENG, TextKeys.NICK)] = "Nick"

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

        map[Pair(Langs.RUS, TextKeys.SOUND_LEVEL)] = "Громкость звуков"
        map[Pair(Langs.ENG, TextKeys.SOUND_LEVEL)] = "Sound volume"

        map[Pair(Langs.RUS, TextKeys.MUSIC_LEVEL)] = "Громкость музыки"
        map[Pair(Langs.ENG, TextKeys.MUSIC_LEVEL)] = "Music volume"

        map[Pair(Langs.RUS, TextKeys.LANGUAGE)] = "Язык"
        map[Pair(Langs.ENG, TextKeys.LANGUAGE)] = "Language"

        map[Pair(Langs.RUS, TextKeys.CARDS)] = "Карты"
        map[Pair(Langs.ENG, TextKeys.CARDS)] = "Cards"

        map[Pair(Langs.RUS, TextKeys.MAIN_MENU)] = "Главное меню"
        map[Pair(Langs.ENG, TextKeys.MAIN_MENU)] = "Main menu"

        return map
    }

    private val langMap = generateLanguages()
    var currLang = Langs.RUS

    fun getText(key: TextKeys) = langMap[Pair(currLang, key)]
}