package com.skosc.tkffintech.misc

import com.skosc.tkffintech.utils.logging.LoggerProvider
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.atomic.AtomicLong

/**
 * Logs intercepted requests of OKHTTP
 */
class OkHttpLoggingInterceptor(private val tag: String) : Interceptor {
    private val requestCounter = AtomicLong(0)
    private val logger = LoggerProvider.get("TKF-OKHttp")

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val id = requestCounter.getAndIncrement()

        logger.debug("Request $id: (${request.method()}) ${request.url()}")

        val response = chain.proceed(request)

        logger.debug("Response $id: ${response.code()}/${response.message()}")

        return response
    }
}