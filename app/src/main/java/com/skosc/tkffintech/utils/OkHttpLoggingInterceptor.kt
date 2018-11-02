package com.skosc.tkffintech.utils

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class OkHttpLoggingInterceptor(private val tag: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        Log.d(tag, "Request: " + request.url())

        val response = chain.proceed(request)

        Log.d(tag, "Response: " + request.url())

        return response
    }

}