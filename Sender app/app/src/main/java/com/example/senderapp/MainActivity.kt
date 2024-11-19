package com.example.senderapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wifiscanner.listener.WifiP2PConnectionCallback
import com.wifiscanner.service.WifiP2PService
import com.wifiscanner.service.WifiP2PServiceImpl
import java.io.IOException
import java.io.OutputStream
import java.net.Socket


class MainActivity : AppCompatActivity()
//    WifiDirectManager.PeerListListener,
//    WifiP2PConnectionCallback
{

//    private lateinit var wifiDirectManager: WifiDirectManager
//    private lateinit var deviceListAdapter: DeviceListAdapter
//    lateinit var wifiP2PService: WifiP2PService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sendBtn = findViewById<Button>(R.id.discover)

        sendBtn.setOnClickListener {
            if (isConnected()) {
                val ipAddress = "192.168.1.16"
                val message = "Hello Dinkar i hope you jdj  "
                SendMessageTask().execute(ipAddress, message)
            } else {
                Toast.makeText(this@MainActivity,"Not connected",Toast.LENGTH_SHORT).show()
            }
        }

//        wifiDirectManager = WifiDirectManager(this)
//
//        // Setup RecyclerView
//        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
//        deviceListAdapter = DeviceListAdapter()
//        recyclerView.adapter = deviceListAdapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        // Discover peers when the activity is created
//        val discoverBtn = findViewById<Button>(R.id.discover)
//        discoverBtn.setOnClickListener {
//         //   wifiDirectManager.discoverPeers()
//
//            val wifiP2PService: WifiP2PService = WifiP2PServiceImpl.Builder()
//                .setReceiver(this)
//                .setWifiP2PConnectionCallback(this)
//                .build()
//            wifiP2PService.onCreate()
//            wifiP2PService.onResume();
//
//        }
    }


//    override fun onResume() {
//        super.onResume()
//    }
//    override fun onDestroy() {
//        super.onDestroy()
//        // Unregister the receiver when the activity is destroyed
//        wifiDirectManager.unregisterReceiver()
//    }
//
//    // Handle UI interactions to connect to a selected peer
//    // For example, on a button click
//    fun onConnectButtonClick(device: WifiP2pDevice) {
//        wifiDirectManager.connect(device)
//    }
//
//    override fun onPeersAvailable(peers: List<WifiP2pDevice>) {
//        if (peers.isEmpty()) {
//            // No peers available, show a message or handle it as needed
//            Toast.makeText(this, "No peers available", Toast.LENGTH_SHORT).show()
//        } else {
//            // Update your UI with the list of available peers
//            deviceListAdapter.updateList(peers)
//        }
//    }
//
//    private inner class DeviceListAdapter : RecyclerView.Adapter<DeviceViewHolder>() {
//
//        private var deviceList: List<WifiP2pDevice> = emptyList()
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
//            val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.item_device, parent, false)
//            return DeviceViewHolder(view)
//        }
//
//        override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
//            val device = deviceList[position]
//            holder.bind(device)
//            holder.itemView.setOnClickListener {
//                // Handle item click (e.g., initiate connection)
//                onConnectButtonClick(device)
//            }
//        }
//
//        override fun getItemCount(): Int {
//            return deviceList.size
//        }
//
//        fun updateList(newList: List<WifiP2pDevice>) {
//            deviceList = newList
//            notifyDataSetChanged()
//        }
//    }
//
//    private class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        fun bind(device: WifiP2pDevice) {
//            itemView.findViewById<TextView>(R.id.deviceName).text = device.deviceName
//            itemView.findViewById<TextView>(R.id.deviceAddress).text = device.deviceAddress
//        }
//    }
//
//    override fun onInitiateDiscovery() {
//
//        Log.d("Discovery","OnItial Discovery")
//    }
//
//    override fun onDiscoverySuccess() {
//        Log.d("Discovery","onDiscoverySuccess")
//
//    }
//
//    override fun onDiscoveryFailure() {
//        Log.d("Discovery","onDiscoveryFailure")
//
//    }
//
//    override fun onPeerAvailable(p0: WifiP2pDeviceList?) {
//        Log.d("Discovery","onPeerAvailable")
//
//    }
//
//    override fun onPeerStatusChanged(p0: WifiP2pDevice?) {
//        Log.d("Discovery","onPeerStatusChanged")
//
//    }
//
//    override fun onPeerConnectionSuccess() {
//        Log.d("Discovery","onPeerConnectionSuccess")
//
//    }
//
//    override fun onPeerConnectionFailure() {
//        Log.d("Discovery","onPeerConnectionFailure")
//
//    }
//
//    override fun onPeerDisconnectionSuccess() {
//        Log.d("Discovery","onPeerDisconnectionSuccess")
//
//    }
//
//    override fun onPeerDisconnectionFailure() {
//        Log.d("Discovery","onPeerDisconnectionFailure")
//
//    }
//
//    override fun onDataTransferring() {
//        Log.d("Discovery","onDataTransferring")
//
//    }
//
//    override fun onDataTransferredSuccess() {
//        Log.d("Discovery","onDataTransferredSuccess")
//
//    }
//
//    override fun onDataTransferredFailure() {
//        Log.d("Discovery","onDataTransferredFailure")
//
//    }
//
//    override fun onDataReceiving() {
//        Log.d("Discovery","onDataReceiving")
//
//    }
//
//    override fun onDataReceivedSuccess(p0: String?) {
//        Log.d("Discovery","onDataReceivedSuccess")
//
//    }
//
//    override fun onDataReceivedFailure() {
//        Log.d("Discovery","onDataReceivedFailure")
//
//    }


    private fun isConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private inner class SendMessageTask : AsyncTask<String, Void, Void>() {
        override fun doInBackground(vararg params: String): Void? {
            val ipAddress = params[0]
            val message = params[1]

            try {
                Socket(ipAddress, 12345).use { socket -> // Use your desired port
                    val outputStream: OutputStream = socket.getOutputStream()
                    outputStream.write(message.toByteArray())
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
    }


}

