package com.skosc.tkffintech.misc

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.atomic.AtomicLong

/**
 * Logs intercepted requests of OKHTTP
 */
class OkHttpLoggingInterceptor(private val tag: String) : Interceptor {
    private val requestCounter = AtomicLong(0)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val id = requestCounter.getAndIncrement()

        log("Request $id: (${request.method()}) ${request.url()}")

        val response = chain.proceed(request)

        log("Response $id: ${response.code()}/${response.message()}")

        return response
    }

    private fun log(msg: String) {
        Log.d(tag, msg)
    }

}