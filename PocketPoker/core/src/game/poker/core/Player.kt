package game.poker.core

import com.google.gson.JsonObject


class Player(val localSeat: Int) {

    var id: Int = -1
    var name: String = ""
    var disconnected: Boolean = false
    var stack: Long = 0
    var gived: Long = 0

    constructor(data: JsonObject, localSeat: Int): this(localSeat) {
        id = data["id"].asInt
        name = data["name"].asString
        disconnected = data["disconnected"].asBoolean
        stack = data["stack"].asLong
    }

    fun allMoney() = stack + gived
}