package game.poker

object Settings{

    enum class Langs{ RUS, ENG }

    enum class CardsType{ COLOR_4, COLOR_2 }

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
        LANG_RUS,
        LANG_ENG,
        CARDS,
        CARD_2_COLOR,
        CARD_4_COLOR,
        MAIN_MENU,
        FINAL_TABLE,
        TABLE,
        ANTE,
        BIG_BLIND,
        SMALL_BLIND,
        FOLD,
        CHECK,
        CALL,
        RAISE,
        ALL_IN,
        SECONDS,
        ANY
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

        map[Pair(Langs.RUS, TextKeys.LANG_RUS)] = "Русский"
        map[Pair(Langs.ENG, TextKeys.LANG_RUS)] = "Русский"

        map[Pair(Langs.RUS, TextKeys.LANG_ENG)] = "English"
        map[Pair(Langs.ENG, TextKeys.LANG_ENG)] = "English"

        map[Pair(Langs.RUS, TextKeys.CARDS)] = "Карты"
        map[Pair(Langs.ENG, TextKeys.CARDS)] = "Cards"

        map[Pair(Langs.RUS, TextKeys.CARD_2_COLOR)] = "2 цвета"
        map[Pair(Langs.ENG, TextKeys.CARD_2_COLOR)] = "2 colors"

        map[Pair(Langs.RUS, TextKeys.CARD_4_COLOR)] = "4 цвета"
        map[Pair(Langs.ENG, TextKeys.CARD_4_COLOR)] = "4 colors"

        map[Pair(Langs.RUS, TextKeys.MAIN_MENU)] = "Главное меню"
        map[Pair(Langs.ENG, TextKeys.MAIN_MENU)] = "Main menu"

        map[Pair(Langs.RUS, TextKeys.FINAL_TABLE)] = "Финальный стол"
        map[Pair(Langs.ENG, TextKeys.FINAL_TABLE)] = "Final table"

        map[Pair(Langs.RUS, TextKeys.TABLE)] = "Стол"
        map[Pair(Langs.ENG, TextKeys.TABLE)] = "Table"

        map[Pair(Langs.RUS, TextKeys.ANTE)] = "Анте"
        map[Pair(Langs.ENG, TextKeys.ANTE)] = "Ante"

        map[Pair(Langs.RUS, TextKeys.BIG_BLIND)] = "ББ"
        map[Pair(Langs.ENG, TextKeys.BIG_BLIND)] = "BB"

        map[Pair(Langs.RUS, TextKeys.SMALL_BLIND)] = "МБ"
        map[Pair(Langs.ENG, TextKeys.SMALL_BLIND)] = "SB"

        map[Pair(Langs.RUS, TextKeys.FOLD)] = "Фолд"
        map[Pair(Langs.ENG, TextKeys.FOLD)] = "Fold"

        map[Pair(Langs.RUS, TextKeys.CHECK)] = "Чек"
        map[Pair(Langs.ENG, TextKeys.CHECK)] = "Check"

        map[Pair(Langs.RUS, TextKeys.CALL)] = "Колл"
        map[Pair(Langs.ENG, TextKeys.CALL)] = "Call"

        map[Pair(Langs.RUS, TextKeys.RAISE)] = "Рейз"
        map[Pair(Langs.ENG, TextKeys.RAISE)] = "Raise"

        map[Pair(Langs.RUS, TextKeys.ALL_IN)] = "Олл-ин"
        map[Pair(Langs.ENG, TextKeys.ALL_IN)] = "All-in"

        map[Pair(Langs.RUS, TextKeys.SECONDS)] = "сек"
        map[Pair(Langs.ENG, TextKeys.SECONDS)] = "sec"

        map[Pair(Langs.RUS, TextKeys.ANY)] = "люб."
        map[Pair(Langs.ENG, TextKeys.ANY)] = "any"

        return map
    }

    private val langMap = generateLanguages()
    var currLang = Langs.RUS
    var currCards = CardsType.COLOR_4

    fun getText(key: TextKeys) = langMap[Pair(currLang, key)]
            ?: throw IllegalArgumentException("Bad text key")
}