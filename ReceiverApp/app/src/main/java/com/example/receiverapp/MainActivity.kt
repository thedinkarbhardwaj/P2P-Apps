package com.example.receiverapp

import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket

class MainActivity : AppCompatActivity() {

    private lateinit var receivedMessageTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val receiverPort = 12345 // Choose the same port number as in Sender
//
//        try {
//            ServerSocket(receiverPort).use { serverSocket ->
//                val clientSocket = serverSocket.accept()
//                clientSocket.getInputStream().use { inputStream ->
//                    val buffer = ByteArray(1024)
//                    val bytesRead = inputStream.read(buffer)
//                    val receivedData = String(buffer, 0, bytesRead)
//                    Log.d("Receiver", "Received: $receivedData")
//                }
//                clientSocket.close()
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }


        val ipAddress = getDeviceIpAddress()
        Log.d("IpAddresssssssssssss", ipAddress.toString())

        val localIpAddress = getLocalIpAddress()
        Log.d("IpAddresssssssssssss_Lo", ipAddress.toString())


        var receivedBtn = findViewById<Button>(R.id.reciveBtn)

        receivedBtn.setOnClickListener {
            receivedMessageTextView = findViewById(R.id.txt)

            ReceiveMessageTask().execute()
        }


    }

    private inner class ReceiveMessageTask : AsyncTask<Void, Void, String?>() {
        override fun doInBackground(vararg voids: Void): String? {
            try {
                ServerSocket(12345).use { serverSocket -> // Use the same port as in the Sender
                    Socket().use { socket ->
                        socket.soTimeout = 5000 // Set a timeout for socket operations
                        socket.bind(null)
                        socket.connect(serverSocket.localSocketAddress, 5000)

                        val bufferedReader = BufferedReader(InputStreamReader(socket.getInputStream()))
                        val message = bufferedReader.readLine()

                        bufferedReader.close()
                        return message
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(message: String?) {
            if (message != null) {
                receivedMessageTextView.setText("dfd" + message)
            }
        }
    }

    private fun getDeviceIpAddress(): String {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo: WifiInfo? = wifiManager.connectionInfo

        if (wifiInfo != null) {
            val ipAddress = wifiInfo.ipAddress
            return String.format(
                "%d.%d.%d.%d",
                ipAddress and 0xff,
                ipAddress shr 8 and 0xff,
                ipAddress shr 16 and 0xff,
                ipAddress shr 24 and 0xff
            )
        }

        return "Not connected to Wi-Fi"
    }


    private fun getLocalIpAddress(): String {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo: WifiInfo? = wifiManager.connectionInfo

        if (wifiInfo != null) {
            val ipAddress = wifiInfo.ipAddress
            return String.format(
                "%d.%d.%d.%d",
                ipAddress and 0xff,
                ipAddress shr 8 and 0xff,
                ipAddress shr 16 and 0xff,
                ipAddress shr 24 and 0xff
            )
        }

        return "Not connected to Wi-Fi"
    }


}