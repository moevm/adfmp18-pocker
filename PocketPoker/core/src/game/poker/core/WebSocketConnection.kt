package game.poker.core

import java.net.URI
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.util.*


class WebSocketConnection(private val queue: Queue<String>,
                          ip: String="188.134.82.95", port: Int = 9001)
    : WebSocketClient(URI("ws://$ip:$port")){

    private var openMsg = ""

    fun connectToServer(openMsg: String){
        if(isOpen){
            close()
        }
        this.openMsg = openMsg
        connect()
    }

    fun closeConnection(){
        if(isOpen){
            close()
        }
    }

    override fun onOpen(handshakedata: ServerHandshake) {
        send(openMsg)
        println("WebSocket send $openMsg")
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        println("WebSocket closed with exit code $code additional info: $reason")
    }

    override fun onMessage(message: String) {
        println("WebSocket receive: $message")
    }

    override fun onError(ex: Exception) {
        println("WebSocket error occurred: $ex")
    }

}