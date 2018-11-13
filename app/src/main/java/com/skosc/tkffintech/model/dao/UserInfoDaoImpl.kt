package com.skosc.tkffintech.model.dao

import android.content.SharedPreferences
import com.google.gson.Gson
import com.skosc.rxprefs.RxPreferences
import com.skosc.rxprefs.SerializerAdapter
import com.skosc.tkffintech.entities.UserInfo
import io.reactivex.Observable


class UserInfoDaoImpl(sp: SharedPreferences, private val gson: Gson) : UserInfoDao {
    companion object {
        private const val CACHE_TIME_SECONDS = 86400
    }

    private val rxSharedPrefs = RxPreferences.create(sp)
    private val userInfoPref = rxSharedPrefs.getObject("user-info", UserInfo(), object : SerializerAdapter<UserInfo> {
        override fun deserialize(value: String): UserInfo = gson.fromJson(value, UserInfo::class.java)
        override fun serialize(value: UserInfo): String = gson.toJson(value)
    })

    private val userCoursesPref = rxSharedPrefs.getStringSet("current-user-curses", setOf())

    override val rxUserInfo: Observable<UserInfo> =
            userInfoPref.observable()


    // TODO Make async
    override fun saveUserInfo(info: UserInfo) {
        userInfoPref.post(info)
    }
}