package com.skosc.tkffintech.utils.logging

import com.skosc.tkffintech.BuildConfig
import kotlin.reflect.KClass

object LoggerProvider {
    private const val PREFIX = "TKF"

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
    fun get(): Logger = createLoggerWithTag("$PREFIX-LOG")

    private fun loggerForObject(context: Any): Logger {
        val shortName = context.javaClass.simpleName
        val id = System.identityHashCode(context)
        val tag = "$PREFIX-$shortName($id)"
        return createLoggerWithTag(tag)
    }

    private fun loggerForString(name: String): Logger {
        return createLoggerWithTag(name)
    }

    private fun loggerForClass(cls: KClass<*>): Logger {
        val shortName = cls.java.simpleName
        val tag = "$PREFIX-$shortName"
        return createLoggerWithTag(tag)
    }

    private fun createLoggerWithTag(tag: String): Logger {
        if (BuildConfig.DEBUG) {
            return DummyLogger()
        }
        return LogcatLogger(tag)
    }
}