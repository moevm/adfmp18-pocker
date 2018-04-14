package game.poker.core.handle

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import game.poker.PocketPoker
import game.poker.core.WebSocketConnection
import java.util.*


class MenuHandler(private val game: PocketPoker) {

    private val queue: Queue<String> = LinkedList<String>()
    private val socket: WebSocketConnection = WebSocketConnection(queue)
    private var inLoop = false
    private val parser = JsonParser()

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

            game.receiveFromServer(data)
        }
    }

    fun sendToServer(json: JsonObject){
        val message = json.toString()
        socket.send(message)
    }

    fun close(){
        inLoop = false
        val json = JsonObject()
        json.addProperty("type", "close")
        sendToServer(json)
        socket.close()
    }

}