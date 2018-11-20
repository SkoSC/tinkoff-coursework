package com.skosc.tkffintech.utils

import io.reactivex.Single
import java.util.concurrent.TimeUnit

object Waiter {
    fun wait(amount: Long, unit: TimeUnit, callback: () -> Unit) {
        val disp = Single.timer(amount, unit)
                .observeOnMainThread()
                .subscribe { _ -> callback() }
    }
}