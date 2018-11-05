package com.skosc.tkffintech.service

import android.content.Context
import android.net.ConnectivityManager

class NetworkInfoService(ctx: Context) {
    private val connectivityservice: ConnectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun checkConnection(): Boolean {
        val networkInfo = connectivityservice.activeNetworkInfo
        return networkInfo.isConnected
    }
}