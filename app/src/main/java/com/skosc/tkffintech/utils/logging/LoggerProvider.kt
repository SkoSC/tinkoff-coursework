package com.skosc.tkffintech.utils.logging

import kotlin.reflect.KClass

object LoggerProvider {
    private const val PREFIX = "TKF"
    private const val STATIC_TAG = "TinkoffFintech"

    /**
     * Returns logger for given context, supports: Strings, KClasses and other arbitrary Objects.
     */
    fun get(context: Any): Logger {
        return when (context) {
            is String -> loggerForString(context)
            is KClass<*> -> loggerForClass(context)
            else -> loggerForObject(context)
        }
    }

    /**
     * Returns logger for anonymous context
     */
    fun get(): Logger = LogcatLogger("$PREFIX-LOG")

    /**
     * Provides singleton logger
     */
    val static: Logger = LogcatLogger(STATIC_TAG)

    private fun loggerForObject(context: Any): Logger {
        val shortName = context.javaClass.simpleName
        val id = System.identityHashCode(context)
        val tag = "$PREFIX-$shortName($id)"
        return LogcatLogger(tag)
    }

    private fun loggerForString(name: String): Logger {
        return LogcatLogger(name)
    }

    private fun loggerForClass(cls: KClass<*>): Logger {
        val shortName = cls.java.simpleName
        val tag = "$PREFIX-$shortName"
        return LogcatLogger(tag)
    }
}