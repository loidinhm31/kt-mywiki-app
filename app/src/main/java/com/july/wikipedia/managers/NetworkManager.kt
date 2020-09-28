package com.july.wikipedia.managers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast

class NetworkManager {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivity = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // Get network info for all of the data interfaces (e.g. WiFi, 3G, LTE, etc.)
        val info = connectivity.allNetworkInfo
        if (connectivity.activeNetworkInfo == null) {
            return false
        }

        // Make sure that there is at least one interface to test against
        if (info != null) {
            // Iterate through the interfaces
            for (i in info.indices) {

                // Check this interface for connected state
                if (info[i].state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
        }
        return false
    }

}