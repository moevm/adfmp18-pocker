package game.poker.core.handle

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import game.poker.PocketPoker
import game.poker.core.WebSocketConnection
import java.util.*


class MenuHandler(private val game: PocketPoker) {

    val queue: Queue<String> = LinkedList<String>()
    val socket: WebSocketConnection = WebSocketConnection(queue)
    var inLoop = false
    val parser = JsonParser()

    init {
        val json = JsonObject()
        json.addProperty("type", "kt")
        socket.connectToServer(json.toString())
        Thread(Runnable { handle() }).start()
    }

    fun handle(){
        inLoop = true

        while (inLoop){
            if(queue.isEmpty()){
                Thread.sleep(100)
                continue
            }

            val data =  parser.parse(queue.remove()).asJsonObject

            println("MENU DATA: $data")
            game.recieveFromServer(data)
        }
    }

    fun sendToServer(json: JsonObject){
        val message = json.toString()
        println("Send to server: $message")
        socket.send(message)
    }

}