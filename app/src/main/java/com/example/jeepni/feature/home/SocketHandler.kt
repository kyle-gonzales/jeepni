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
            val uri = "https://jeepnibackend-utgofbd3aa-as.a.run.app/"
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