package com.july.wikipedia.managers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi


class NetworkManager {

    @RequiresApi(Build.VERSION_CODES.M)
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivity = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // Get network info for all of the data interfaces (e.g. WiFi, 3G, LTE, etc.)
        val info = connectivity.allNetworks

        if (connectivity.activeNetwork == null) {
            return false
        }

        // Make sure that there is at least one interface to test against
        for (i in info.indices) {

            val networkCapabilities: NetworkCapabilities =
                connectivity.getNetworkCapabilities(info[i])!!

            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && networkCapabilities.hasCapability(NET_CAPABILITY_VALIDATED))
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
                    return true
        }
        return false
    }

}