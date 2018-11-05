package com.skosc.tkffintech.service

import android.content.Context
import android.net.ConnectivityManager
import com.skosc.tkffintech.utils.subscribeOnIoThread
import io.reactivex.Single
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress

class NetworkInfoService(ctx: Context) {
    private val connectivityservice: ConnectivityManager
            = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun checkConnection(): Boolean {
        val networkInfo = connectivityservice.activeNetworkInfo
        return networkInfo.isConnected
    }
}