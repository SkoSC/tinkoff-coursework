package com.skosc.tkffintech.model.dao

import android.content.SharedPreferences
import com.google.gson.Gson
import com.skosc.tkffintech.entities.UserInfo
import io.reactivex.subjects.BehaviorSubject


class UserInfoDaoImpl(private val sp: SharedPreferences, private val gson: Gson) : UserInfoDao {
    companion object {
        private const val KEY_USER_INFO = "user-info"
    }

    override val rxUserInfo: BehaviorSubject<UserInfo> = BehaviorSubject.create()

    init {
        val json = sp.getString(KEY_USER_INFO, "")
        val userInfo = gson.fromJson<UserInfo>(json, UserInfo::class.java) ?: UserInfo()
        rxUserInfo.onNext(userInfo)
    }

    override fun saveUserInfo(info: UserInfo) {
        sp.edit()
                .putString(KEY_USER_INFO, gson.toJson(info))
                .apply()
        rxUserInfo.onNext(info)
    }
}