package com.skosc.tkffintech.utils.logging

class DummyLogger : Logger {
    override fun error(msg: String) {}
    override fun error(throwable: Throwable) {}
    override fun warning(msg: String) {}
    override fun warning(throwable: Throwable) {}
    override fun info(msg: String) {}
    override fun debug(msg: String) {}
    override fun verbose(msg: String) {}
}