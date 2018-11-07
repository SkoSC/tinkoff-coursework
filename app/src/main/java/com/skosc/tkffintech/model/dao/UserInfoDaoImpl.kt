package com.skosc.tkffintech.model.dao

import android.content.SharedPreferences
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.google.gson.Gson
import com.skosc.tkffintech.entities.UserInfo
import com.skosc.tkffintech.model.entity.ExpirationTimer
import io.reactivex.Maybe
import org.joda.time.DateTime

class UserInfoDaoImpl(sp: SharedPreferences, private val gson: Gson) : UserInfoDao {
    companion object {
        private const val CACHE_TIME_SECONDS = 86400
    }

    private val rxSharedPrefs = RxSharedPreferences.create(sp)
    private val userInfoJsonPref = rxSharedPrefs.getString("user-info", "")
    private val userInfoCacheExpiration = ExpirationTimer.create(sp, "user-info-exp")


    override val userInfo: Maybe<UserInfo>
        get() = userInfoCacheExpiration
                .isExpired.filter { !it }
                .map {
                    val json = userInfoJsonPref.get()
                    if (json.isEmpty()) {
                        return@map null
                    }
                    gson.fromJson(json, UserInfo::class.java)
                }

    // TODO Make async
    override fun saveUserInfo(info: UserInfo) {
        userInfoCacheExpiration.rewind(DateTime.now().plusSeconds(CACHE_TIME_SECONDS))
        val json = gson.toJson(info)
        userInfoJsonPref.set(json)
    }
}