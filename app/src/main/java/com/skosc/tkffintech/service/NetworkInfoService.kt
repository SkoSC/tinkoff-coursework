package com.skosc.tkffintech.service

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

class NetworkInfoService(ctx: Context) {
    companion object {
        private const val TAG = "TKF_SERVICE_NETWORKINFO"
    }

    private val connectivityService: ConnectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun checkConnection(): Boolean {
        val networkInfo = connectivityService.activeNetworkInfo
        Log.w(TAG, "Network connection date: is_connected=${networkInfo.isConnected}")
        return networkInfo.isConnected
    }
}