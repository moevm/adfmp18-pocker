package game.poker.core

import java.net.URI
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake


class SocketConnection(ip: String="188.134.82.95", port: Int = 9001)
    : WebSocketClient(URI("ws://$ip:$port")){

    override fun onOpen(handshakedata: ServerHandshake) {
        send("Hello, it is me. Mario :)")
        println("new connection opened")
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        println("closed with exit code $code additional info: $reason")
    }

    override fun onMessage(message: String) {
        println("received message: " + message)
    }

    override fun onError(ex: Exception) {
        System.err.println("an error occurred:" + ex)
    }

}