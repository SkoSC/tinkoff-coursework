package com.skosc.tkffintech.model.service

import android.content.Context
import android.net.ConnectivityManager
import com.skosc.tkffintech.utils.logging.LoggerProvider

class NetworkInfoService(ctx: Context) {

    private val logger = LoggerProvider.get("NETWORK_INFO")

    private val connectivityService: ConnectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /**
     * Checks device connection to internet
     */
    fun checkConnection(): Boolean {
        val networkInfo = connectivityService.activeNetworkInfo
        logger.debug("Network connection date: is_connected=${networkInfo.isConnected}")
        return networkInfo.isConnected
    }
}