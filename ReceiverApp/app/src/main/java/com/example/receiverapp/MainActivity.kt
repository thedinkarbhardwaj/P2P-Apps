package com.example.receiverapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.IOException
import java.net.ServerSocket

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val receiverPort = 12345 // Choose the same port number as in Sender

        try {
            ServerSocket(receiverPort).use { serverSocket ->
                val clientSocket = serverSocket.accept()
                clientSocket.getInputStream().use { inputStream ->
                    val buffer = ByteArray(1024)
                    val bytesRead = inputStream.read(buffer)
                    val receivedData = String(buffer, 0, bytesRead)
                    Log.d("Receiver", "Received: $receivedData")
                }
                clientSocket.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}