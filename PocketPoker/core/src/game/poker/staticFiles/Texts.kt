package game.poker.staticFiles

import game.poker.Settings


object Texts {

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
        LANG_RUS,
        LANG_ENG,
        CARDS,
        CARD_2_COLOR,
        CARD_4_COLOR,
        ORIENTATION,
        ORIENT_HOR,
        ORIENT_VERT,
        ORIENT_GYRO,
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
        ANY,
        POT,
        EMPTY_SEAT,
        CHAT,
        INFO,
        OPENED,
        CLOSED,
        PLAYERS,
        HANDS,
        CHIPS,
        BLINDS,
        CREATE_TOURNAMENT,
        REGISTRATION_ERROR,
        NICK_IS_USED,
        TABLES,
        TITLE,
        BOTS,
        CREATE,
        TABLE_PLAYERS,
        START_BLINDS,
        BLIND_SPEED,
        PASSWORD,
        STANDARD,
        FAST,
        RAPID,
        BULLET,
        BOTS_MORE_THAN_PLAYERS,
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

        map[Pair(Langs.RUS, TextKeys.ORIENTATION)] = "Ориентация стола"
        map[Pair(Langs.ENG, TextKeys.ORIENTATION)] = "Table orientation"

        map[Pair(Langs.RUS, TextKeys.ORIENT_HOR)] = "Горизонтальная"
        map[Pair(Langs.ENG, TextKeys.ORIENT_HOR)] = "Horizontal"

        map[Pair(Langs.RUS, TextKeys.ORIENT_VERT)] = "Вертикальная"
        map[Pair(Langs.ENG, TextKeys.ORIENT_VERT)] = "Vertical"

        map[Pair(Langs.RUS, TextKeys.ORIENT_GYRO)] = "По гироскопу"
        map[Pair(Langs.ENG, TextKeys.ORIENT_GYRO)] = "By gyro"

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

        map[Pair(Langs.RUS, TextKeys.POT)] = "Банк"
        map[Pair(Langs.ENG, TextKeys.POT)] = "Pot"

        map[Pair(Langs.RUS, TextKeys.EMPTY_SEAT)] = "Место пусто"
        map[Pair(Langs.ENG, TextKeys.EMPTY_SEAT)] = "Empty seat"

        map[Pair(Langs.RUS, TextKeys.CHAT)] = "Чат"
        map[Pair(Langs.ENG, TextKeys.CHAT)] = "Chat"

        map[Pair(Langs.RUS, TextKeys.INFO)] = "Инфо"
        map[Pair(Langs.ENG, TextKeys.INFO)] = "Info"

        map[Pair(Langs.RUS, TextKeys.OPENED)] = "Открытый"
        map[Pair(Langs.ENG, TextKeys.OPENED)] = "Opened"

        map[Pair(Langs.RUS, TextKeys.CLOSED)] = "Закрытый"
        map[Pair(Langs.ENG, TextKeys.CLOSED)] = "Closed"

        map[Pair(Langs.RUS, TextKeys.PLAYERS)] = "Игроков"
        map[Pair(Langs.ENG, TextKeys.PLAYERS)] = "Players"

        map[Pair(Langs.RUS, TextKeys.CHIPS)] = "Фишек"
        map[Pair(Langs.ENG, TextKeys.CHIPS)] = "Chips"

        map[Pair(Langs.RUS, TextKeys.HANDS)] = "Сыграно рук"
        map[Pair(Langs.ENG, TextKeys.HANDS)] = "Hands played"

        map[Pair(Langs.RUS, TextKeys.BLINDS)] = "Блайнды"
        map[Pair(Langs.ENG, TextKeys.BLINDS)] = "Blinds"

        map[Pair(Langs.RUS, TextKeys.CREATE_TOURNAMENT)] = "Создать турнир"
        map[Pair(Langs.ENG, TextKeys.CREATE_TOURNAMENT)] = "New tournament"

        map[Pair(Langs.RUS, TextKeys.REGISTRATION_ERROR)] = "Ошибка регистрации"
        map[Pair(Langs.ENG, TextKeys.REGISTRATION_ERROR)] = "Registration error"

        map[Pair(Langs.RUS, TextKeys.NICK_IS_USED)] = "Такой никнейм уже занят"
        map[Pair(Langs.ENG, TextKeys.NICK_IS_USED)] = "Nick is already used"

        map[Pair(Langs.RUS, TextKeys.TABLES)] = "Столов"
        map[Pair(Langs.ENG, TextKeys.TABLES)] = "Tables"

        map[Pair(Langs.RUS, TextKeys.TITLE)] = "Название"
        map[Pair(Langs.ENG, TextKeys.TITLE)] = "Title"

        map[Pair(Langs.RUS, TextKeys.BOTS)] = "Ботов"
        map[Pair(Langs.ENG, TextKeys.BOTS)] = "Bots"

        map[Pair(Langs.RUS, TextKeys.CREATE)] = "Создать"
        map[Pair(Langs.ENG, TextKeys.CREATE)] = "Create"

        map[Pair(Langs.RUS, TextKeys.TABLE_PLAYERS)] = "Игроков за столом"
        map[Pair(Langs.ENG, TextKeys.TABLE_PLAYERS)] = "Players at a table"

        map[Pair(Langs.RUS, TextKeys.START_BLINDS)] = "Стартовые блайнды"
        map[Pair(Langs.ENG, TextKeys.START_BLINDS)] = "Start blinds"

        map[Pair(Langs.RUS, TextKeys.BLIND_SPEED)] = "Скорость роста блайндов"
        map[Pair(Langs.ENG, TextKeys.BLIND_SPEED)] = "Blind speed"

        map[Pair(Langs.RUS, TextKeys.PASSWORD)] = "Пароль"
        map[Pair(Langs.ENG, TextKeys.PASSWORD)] = "Password"

        map[Pair(Langs.RUS, TextKeys.STANDARD)] = "Стандартная"
        map[Pair(Langs.ENG, TextKeys.STANDARD)] = "Standard"

        map[Pair(Langs.RUS, TextKeys.FAST)] = "Ускоренная"
        map[Pair(Langs.ENG, TextKeys.FAST)] = "Fast"

        map[Pair(Langs.RUS, TextKeys.RAPID)] = "Быстрая"
        map[Pair(Langs.ENG, TextKeys.RAPID)] = "Rapid"

        map[Pair(Langs.RUS, TextKeys.BULLET)] = "Молниеносная"
        map[Pair(Langs.ENG, TextKeys.BULLET)] = "Bullet"

        map[Pair(Langs.RUS, TextKeys.BOTS_MORE_THAN_PLAYERS)] = "Количество ботов не должно\nпревышать количество игроков"
        map[Pair(Langs.ENG, TextKeys.BOTS_MORE_THAN_PLAYERS)] = "Bots amount must not be greater\nthan players amount"

        return map
    }

    private val langMap = generateLanguages()

    operator fun get(key: TextKeys) = langMap[Pair(Settings.currLang, key)]
            ?: throw IllegalArgumentException("Bad text key")
}