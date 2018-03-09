package game.poker.core

import java.net.URI
import tech.gusavila92.websocketclient.WebSocketClient
import java.util.*


class WebSocketConnection(private val queue: Queue<String>,
                          ip: String="188.134.82.95", port: Int = 9001)
    : WebSocketClient(URI("ws://$ip:$port")){

    private var openMsg = ""
    var clean = false

    fun connectToServer(openMsg: String){
        this.openMsg = openMsg
        connect()
    }

    override fun onOpen() {
        send(openMsg)
        println("WebSocket send $openMsg")
    }

    override fun onTextReceived(message: String) {
        println("WebSocket recieve $message")
        queue.add(message)
    }

    override fun onBinaryReceived(data: ByteArray) {
        println("onBinaryReceived")
    }

    override fun onPingReceived(data: ByteArray) {
        println("onPingReceived")
    }

    override fun onPongReceived(data: ByteArray) {
        println("onPongReceived")
    }

    override fun onException(e: Exception) {
        println(e.message)
    }

    override fun onCloseReceived() {
        println("onCloseReceived")
    }

}