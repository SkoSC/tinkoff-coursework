package com.skosc.tkffintech.model.dao

import com.skosc.tkffintech.entities.UserInfo
import io.reactivex.Maybe

interface UserInfoDao {
    val userInfo: Maybe<UserInfo>
    fun saveUserInfo(info: UserInfo)
}