package com.example.jeepni.feature.home

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {

    lateinit var mSocket: Socket

    @Synchronized
    fun setSocket() {
        try {
            val uri = "http://192.168.85.135:1234/"
//            val uri = "http://172.16.13.32:1234"
//            val uri = "http://192.168.1.8:1234" // kyle's laptop
            //val uri = "http://192.168.1.25:1234/" // kyle's computer
            mSocket = IO.socket(uri)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }
    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
        mSocket.send("connected!")
        Log.i("connection", "connected!")
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }
}