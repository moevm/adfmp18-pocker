package game.poker.staticFiles
import com.badlogic.gdx.graphics.Texture

import game.poker.Settings
import game.poker.core.Rank
import game.poker.core.Suit
import game.poker.core.Visibility
import game.poker.core.Card
import game.poker.core.Chip

object Textures{

    val menuBg: Texture by lazy { Texture("pics/bgFullHD2.png") }
    val menuButton: Texture by lazy { Texture("pics/buttonGray.png") }
    val menuButtonDown: Texture by lazy { Texture("pics/buttonGrayDown.png") }
    val edit: Texture by lazy { Texture("pics/edit.png") }
    val editCursor: Texture by lazy { Texture("pics/editCursor.png") }
    val scroll: Texture by lazy { Texture("pics/scroll.png") }
    val list_selection: Texture by lazy { Texture("pics/list.png") } //TODO: добавить текстуры для List и Scroll
    val scrollBg: Texture by lazy { Texture("pics/white.png") }
    val hScroll: Texture by lazy { scroll }
    val hScrollKnob: Texture by lazy { scroll }
    val vScroll: Texture by lazy { scroll }
    val vScrollKnob: Texture by lazy { scroll }
    val sliderBg: Texture by lazy { Texture("pics/sliderBg.png") }
    val sliderKnob: Texture by lazy { Texture("pics/slider.png") }
    val next: Texture by lazy { Texture("pics/menu/next.png") }
    val watch: Texture by lazy { Texture("pics/menu/watch.png") }
    val blinds: Texture by lazy { Texture("pics/menu/blinds.png") }
    val players: Texture by lazy { Texture("pics/menu/players.png") }
    val tables: Texture by lazy { Texture("pics/menu/tables.png") }
    val hands: Texture by lazy { Texture("pics/menu/hands.png") }
    val locked: Texture by lazy { Texture("pics/menu/locked.png") }
    val unlocked: Texture by lazy { Texture("pics/menu/unlocked.png") }
    val exitButton: Texture by lazy { Texture("pics/exit.png") }
    val exitButtonDown: Texture by lazy { Texture("pics/exitDown.png") }
    val labelBg: Texture by lazy { Texture("pics/labelBg.png") }
    val labelBgActive: Texture by lazy { Texture("pics/labelBgActive.png") }
    val labelBgDisabled: Texture by lazy { Texture("pics/labelBgDisabled.png") }
    val chatButton: Texture by lazy { Texture("pics/icon buttons/chat.png") }
    val chatButtonDown: Texture by lazy { Texture("pics/icon buttons/chat clicked.png") }
    val infoButton: Texture by lazy { Texture("pics/icon buttons/info.png") }
    val infoButtonDown: Texture by lazy { Texture("pics/icon buttons/info clicked.png") }
    val nextHand: Texture by lazy { Texture("pics/icon buttons/next hand.png") }
    val nextHandDown: Texture by lazy { Texture("pics/icon buttons/next hand clicked.png") }
    val prevHand: Texture by lazy { Texture("pics/icon buttons/prev hand.png") }
    val prevHandDown: Texture by lazy { Texture("pics/icon buttons/prev hand clicked.png") }
    val nextStep: Texture by lazy { Texture("pics/icon buttons/next step.png") }
    val nextStepDown: Texture by lazy { Texture("pics/icon buttons/next step clicked.png") }
    val playButton: Texture by lazy { Texture("pics/icon buttons/play.png") }
    val playButtonDown: Texture by lazy { Texture("pics/icon buttons/play clicked.png") }
    val pauseButton: Texture by lazy { Texture("pics/icon buttons/pause.png") }
    val pauseButtonDown: Texture by lazy { Texture("pics/icon buttons/pause clicked.png") }

    private fun createCards() : Map<Pair<Settings.CardsType, Card>, Lazy<Texture>>{
        val map = mutableMapOf<Pair<Settings.CardsType, Card>, Lazy<Texture>>()
        for ((cardsType, packPath) in mapOf(Settings.CardsType.COLOR_2 to "2color",
                                            Settings.CardsType.COLOR_4 to "4color")){
            for(rank in Rank.values()){
                for (suit in Suit.values()){
                    map[Pair(cardsType, Card(rank, suit, Visibility.Open))] =
                        lazy { Texture("pics/cards/$packPath/open/${rank.r}${suit.s}.png") }
                    map[Pair(cardsType, Card(rank, suit, Visibility.Hidden))] =
                        lazy { Texture("pics/cards/$packPath/hidden/${rank.r}${suit.s}.png") }
                }
            }
        }
        return map
    }

    private val cardsMap: Map<Pair<Settings.CardsType, Card>, Lazy<Texture>> by lazy { createCards() }
    fun getCard(card: Card): Texture = cardsMap[Pair(Settings.currCards, card)]?.value
            ?: throw IllegalArgumentException("Card texture does not exists: $card")

    val pokerTable: Texture by lazy { Texture("pics/poker_table.png") }
    val cardBackground: Texture by lazy { Texture("pics/cards/4color/open/UP.png") }
    val cardPlaceholder: Texture by lazy { Texture("pics/cards/4color/open/ZZ.png") }

    private fun createChips() : Map<Chip, Lazy<Texture>>{
        val map = mutableMapOf<Chip, Lazy<Texture>>()
        for (chip in Chip.values()){
            map[chip] = lazy { Texture("pics/chips/${chip.path}.png") }
        }
        return map
    }

    private val chipsMap: Map<Chip, Lazy<Texture>> by lazy { createChips() }
    fun getChip(chip: Chip): Texture = chipsMap[chip]?.value
            ?: throw IllegalArgumentException("Chip texture does not exists: $chip")
}
