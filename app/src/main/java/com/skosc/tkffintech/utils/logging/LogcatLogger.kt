package com.skosc.tkffintech.utils.logging

import android.util.Log

class LogcatLogger(private val tag: String) : Logger {
    override fun error(msg: String) {
        Log.e(tag, msg)
    }

    override fun error(throwable: Throwable) {
        Log.e(tag, throwable.message, throwable)
    }

    override fun warning(msg: String) {
        Log.w(tag, msg)
    }

    override fun warning(throwable: Throwable) {
        Log.w(tag, throwable.message, throwable)
    }

    override fun info(msg: String) {
        Log.i(tag, msg)
    }

    override fun debug(msg: String) {
        Log.d(tag, msg)
    }

    override fun verbose(msg: String) {
        Log.v(tag, msg)
    }
}