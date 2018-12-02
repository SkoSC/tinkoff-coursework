package com.skosc.tkffintech.model.entity

import android.content.SharedPreferences
import com.skosc.tkffintech.utils.extensions.subscribeOnIoThread
import io.reactivex.Single
import org.joda.time.DateTime
import java.util.concurrent.TimeUnit

class ExpirationTimer private constructor(private val sp: SharedPreferences, private val name: String) {
    companion object {
        fun create(sp: SharedPreferences, name: String): ExpirationTimer {
            return ExpirationTimer(sp, name)
        }
    }

    private fun rewind(time: DateTime) {
        sp.edit().putLong(name, time.millis)
                .apply()
    }

    fun rewindForward(amount: Long, unit: TimeUnit) {
        val newTime = DateTime.now().plus(unit.toMillis(amount))
        rewind(newTime)
    }

    val isExpired: Single<Boolean>
        get() = Single.fromCallable {
            val timestamp = sp.getLong(name, 0)
            val dateTime = DateTime(timestamp)
            dateTime < DateTime.now()
        }.subscribeOnIoThread()
}