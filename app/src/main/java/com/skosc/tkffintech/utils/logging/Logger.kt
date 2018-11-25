package com.skosc.tkffintech.utils.logging

interface Logger {
    fun error(msg: String)
    fun error(throwable: Throwable)

    fun warning(msg: String)
    fun warning(throwable: Throwable)

    fun info(msg: String)

    fun debug(msg: String)

    fun verbose(msg: String)
}