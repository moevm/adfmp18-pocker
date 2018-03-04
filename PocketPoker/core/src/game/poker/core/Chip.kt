package game.poker.core

enum class Chip(val path: String){
    C_1("1"),
    C_5("2"),
    C_25("3"),
    C_100("4"),
    C_500("5"),
    C_1K("6"),
    C_5K("7"),
    C_25K("8"),
    C_100K("9"),
    C_500K("10"),
    C_1M("11"),
    C_5M("12"),
    C_25M("13"),
    C_100M("14"),
    C_250M("15"),
    C_1B("16"),
    DEALER("D");

    fun price(): Int = when(this){
        C_1 -> 1
        C_5 -> 5
        C_25 -> 25
        C_100 -> 100
        C_500 -> 500
        C_1K -> 1_000
        C_5K -> 5_000
        C_25K -> 25_000
        C_100K -> 100_000
        C_500K -> 500_000
        C_1M -> 1_000_000
        C_5M -> 5_000_000
        C_25M -> 25_000_000
        C_100M -> 100_000_000
        C_250M -> 250_000_000
        C_1B -> 1_000_000_000
        else -> throw IllegalArgumentException("This chip has no price: $this")
    }
}