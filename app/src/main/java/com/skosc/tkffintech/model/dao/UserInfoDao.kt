package com.skosc.tkffintech.model.dao

import com.skosc.tkffintech.entities.UserInfo
import io.reactivex.Observable

interface UserInfoDao {
    /**
     * Object containing information about current user. If user is not currently set, calls onError
     */
    val userInfo: Observable<UserInfo>

    /**
     * Sets current user info and stores it to db
     */
    fun saveUserInfo(info: UserInfo)
}