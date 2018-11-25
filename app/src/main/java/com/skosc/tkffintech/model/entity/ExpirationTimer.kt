package com.skosc.tkffintech.model.entity

import android.content.SharedPreferences
import com.skosc.tkffintech.utils.extensions.subscribeOnIoThread
import io.reactivex.Single
import org.joda.time.DateTime

class ExpirationTimer private constructor(private val sp: SharedPreferences, private val name: String) {
    companion object {
        fun create(sp: SharedPreferences, name: String): ExpirationTimer {
            return ExpirationTimer(sp, name)
        }
    }

    fun rewind(time: DateTime) {
        sp.edit().putLong(name, time.millis)
                .apply()
    }

    val isExpired: Single<Boolean>
        get() = Single.fromCallable {
            val timestamp = sp.getLong(name, 0)
            val dateTime = DateTime(timestamp)
            dateTime < DateTime.now()
        }.subscribeOnIoThread()
}