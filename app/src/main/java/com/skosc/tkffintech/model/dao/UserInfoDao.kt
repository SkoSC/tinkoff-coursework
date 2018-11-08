package com.skosc.tkffintech.model.dao

import com.skosc.tkffintech.entities.UserInfo
import io.reactivex.Maybe
import io.reactivex.Observable

interface UserInfoDao {
    val userInfo: Maybe<UserInfo>
    val rxUserInfo: Observable<UserInfo>
    fun saveUserInfo(info: UserInfo)
}