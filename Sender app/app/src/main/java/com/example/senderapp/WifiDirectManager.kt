package com.example.senderapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.Channel
import android.os.Looper
import android.util.Log

class WifiDirectManager(private val context: Context, private val peerListListener: PeerListListener? = null) {

    interface PeerListListener {
        fun onPeersAvailable(peers: List<WifiP2pDevice>)
    }

    private val wifiP2pManager: WifiP2pManager =
        context.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager

    private val channel: Channel = wifiP2pManager.initialize(context, Looper.getMainLooper(), null)

    private val receiver: BroadcastReceiver

    init {
        receiver = WifiDirectBroadcastReceiver()
        registerReceiver()
    }

    fun discoverPeers() {
        wifiP2pManager.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                Log.d("discoverPeer", "Discover Start")
            }

            override fun onFailure(reason: Int) {
                Log.d("discoverPeer", "Discover End")
            }
        })
    }

    fun connect(device: WifiP2pDevice) {
        val config = WifiP2pConfig()
        config.deviceAddress = device.deviceAddress
        wifiP2pManager.connect(channel, config, null)
    }

    fun unregisterReceiver() {
        context.unregisterReceiver(receiver)
    }

    private fun registerReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
        context.registerReceiver(receiver, intentFilter)
    }

    private inner class WifiDirectBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                    // Handle changes in the list of available peers
                    requestPeers()
                }
                WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                    // Handle changes in connection state
                    // You may want to check if the connection is established successfully
                }
                WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                    // Handle changes in the local device's details
                }
            }
        }

        private fun requestPeers() {
            wifiP2pManager.requestPeers(channel) { peerList ->
                peerListListener?.onPeersAvailable(peerList.deviceList.toList())
            }
        }
    }
}
