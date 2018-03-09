package game.poker.core.handle

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import game.poker.PocketPoker
import game.poker.core.WebSocketConnection
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.*
import java.util.concurrent.TimeUnit


class MenuHandler(private val game: PocketPoker) {

    val queue: Queue<String> = LinkedList<String>()
    val socket: WebSocketConnection = WebSocketConnection(queue)
    var inLoop = false
    val parser = JsonParser()

    init {
        val json = JsonObject()
        json.addProperty("type", "kt")
        socket.connectToServer(json.toString())
        launch {
            handle()
        }
    }

    suspend fun handle(){
        inLoop = true

        while (inLoop){
            if(queue.isEmpty()){
                delay(1000L, TimeUnit.MILLISECONDS)
                println("TIME")
                continue
            }

            val data =  parser.parse(queue.remove()).asJsonObject

            println("MENU DATA: ${data.asString}")
            game.recieveFromServer(data)
        }
    }

    fun sendToServer(json: JsonObject){
        socket.send(json.asString)
    }

}