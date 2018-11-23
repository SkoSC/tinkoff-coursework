package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.entities.UserInfo
import io.reactivex.Observable
import io.reactivex.Single

interface CurrentUserRepo {
    fun login(email: String, password: String): Single<Boolean>
    fun signout()
    val isLoggedIn: Observable<Boolean>
    val info: Observable<UserInfo>
    val id: Observable<Long>
    val idBlocking: Long?
    fun forceRefreshUserInfo()
}