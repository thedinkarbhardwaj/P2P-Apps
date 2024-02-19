package com.example.senderapp

import android.net.wifi.p2p.nsd.WifiP2pServiceRequest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.Socket

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val receiverIpAddress = "106.201.193.6"
        val receiverPort = 12345 // Choose a port number

        try {

            GlobalScope.launch(Dispatchers.IO) {
                Socket(receiverIpAddress, receiverPort).use { socket ->
                    socket.getOutputStream().use { outputStream ->
                        val dataToSend = "Hello, Receiver!"
                        outputStream.write(dataToSend.toByteArray())
                    }
                }

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}