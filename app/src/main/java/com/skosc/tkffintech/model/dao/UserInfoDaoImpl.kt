package com.skosc.tkffintech.model.dao

import android.content.SharedPreferences
import com.google.gson.Gson
import com.skosc.tkffintech.entities.UserInfo
import com.skosc.tkffintech.utils.GlobalConstants.SharedPrefs
import io.reactivex.subjects.BehaviorSubject

class UserInfoDaoImpl(private val sp: SharedPreferences, private val gson: Gson) : UserInfoDao {
    override val rxUserInfo: BehaviorSubject<UserInfo> = BehaviorSubject.create()

    init {
        val json = sp.getString(SharedPrefs.UserInfo.KEY_OBJECT, "")
        val userInfo = gson.fromJson<UserInfo>(json, UserInfo::class.java) ?: UserInfo()
        rxUserInfo.onNext(userInfo)
    }

    override fun saveUserInfo(info: UserInfo) {
        sp.edit()
                .putString(SharedPrefs.UserInfo.KEY_OBJECT, gson.toJson(info))
                .apply()
        rxUserInfo.onNext(info)
    }
}