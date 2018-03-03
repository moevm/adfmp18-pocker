package game.poker.staticFiles
import com.badlogic.gdx.graphics.Texture

import game.poker.Settings
import game.poker.core.Rank
import game.poker.core.Suit
import game.poker.core.Visibility
import game.poker.core.Card
import game.poker.core.Chip

object Textures{
    val menuBg = Texture("pics/MenuBackground.png")
    val menuButton = Texture("pics/button.png")
    val menuButtonDown = Texture("pics/buttonDown.png")
    val edit = Texture("pics/edit.png")
    val editCursor = Texture("pics/editCursor.png")
    val scroll = Texture("pics/scroll.png")
    val list_selection = scroll //TODO: добавить текстуры для List и Scroll
    val scrollBg = scroll
    val hScroll = scroll
    val hScrollKnob = scroll
    val vScroll = scroll
    val vScrollKnob = scroll
    val sliderBg = Texture("pics/sliderBg.png")
    val sliderKnob = Texture("pics/slider.png")
    val exitButton = Texture("pics/exit.png")
    val exitButtonDown = Texture("pics/exitDown.png")
    val labelBg = Texture("pics/labelBg.png")

    private fun createCards() : Map<Pair<Settings.CardsType, Card>, Texture>{
        val map = mutableMapOf<Pair<Settings.CardsType, Card>, Texture>()
        for (packPath in arrayOf("4color")){ // todo: add 2color
            for(rank in Rank.values()){
                for (suit in Suit.values()){

                    map[Pair(Settings.CardsType.COLOR_4, Card(rank, suit, Visibility.Open))] =
                        Texture("pics/cards/$packPath/open/${rank.r}${suit.s}.png")
                    map[Pair(Settings.CardsType.COLOR_4, Card(rank, suit, Visibility.Hidden))] =
                        Texture("pics/cards/$packPath/hidden/${rank.r}${suit.s}.png")

                    map[Pair(Settings.CardsType.COLOR_2, Card(rank, suit, Visibility.Open))] =
                        map[Pair(Settings.CardsType.COLOR_4, Card(rank, suit, Visibility.Open))]!!
                    map[Pair(Settings.CardsType.COLOR_2, Card(rank, suit, Visibility.Hidden))] =
                        map[Pair(Settings.CardsType.COLOR_4, Card(rank, suit, Visibility.Hidden))]!!

                }
            }
        }
        return map
    }

    private val cardsMap: Map<Pair<Settings.CardsType, Card>, Texture> = createCards()
    fun getCard(card: Card) = cardsMap[Pair(Settings.currCards, card)]
            ?: throw IllegalArgumentException("Card texture does not exists: $card")

    val pokerTable = Texture("pics/poker_table.png")
    val cardBackground = Texture("pics/cards/4color/open/UP.png")
    val cardPlaceholder = Texture("pics/cards/4color/open/ZZ.png")

    private fun createChips() : Map<Chip, Texture>{
        val map = mutableMapOf<Chip, Texture>()
        for (chip in Chip.values()){
            map[chip] = Texture("pics/chips/${chip.path}.png")
        }
        return map
    }

    private val chipsMap: Map<Chip, Texture> = createChips()
    fun getChip(chip: Chip) = chipsMap[chip]
            ?: throw IllegalArgumentException("Chip texture does not exists: $chip")
}
