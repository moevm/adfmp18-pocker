package game.poker.core

enum class Rank(val r: Char){
    Ace('A'), King('K'), Queen('Q'), Jack('J'),
    Ten('T'), Nine('9'), Eight('8'), Seven('7'),
    Six('6'), Five('5'), Four('4'), Three('3'), Two('2')
}

enum class Suit(val s: Char){
    Hearts('H'), Diamonds('D'), Clubs('C'), Spades('S')
}

enum class Visibility{
    Open, Hidden
}

data class Card(val rank: Rank, val suit: Suit, val visibility: Visibility)