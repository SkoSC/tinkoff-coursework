package com.skosc.tkffintech.misc

import com.skosc.tkffintech.utils.extensions.observeOnMainThread
import io.reactivex.Single
import java.util.concurrent.TimeUnit

/**
 * Methods for delayed execution
 */
object Waiter {

    /**
     * Wait for some time, and than execute callback om main thread
     */
    fun wait(amount: Long, unit: TimeUnit, callback: () -> Unit) {
        val disp = Single.timer(amount, unit)
                .observeOnMainThread()
                .subscribe { _ -> callback() }
    }
}