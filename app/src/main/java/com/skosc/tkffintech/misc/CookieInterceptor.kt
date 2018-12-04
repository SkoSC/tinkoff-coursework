package com.skosc.tkffintech.misc

import okhttp3.Interceptor
import okhttp3.Response
import java.net.CookieStore

class CookieInterceptor(private val cookieStore: CookieStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()


        val fintechCookies = cookieStore.cookies

        val cookiesHeaderValue = (fintechCookies)
                .map { it.toString() }
                .distinct()
                .joinToString(separator = ";")


        val newRequest = request.newBuilder()
                .removeHeader("Cookie")
                .addHeader("Cookie", "$cookiesHeaderValue;")
                .build()

        return chain.proceed(newRequest)
    }
}