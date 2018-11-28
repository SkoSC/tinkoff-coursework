package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.UserInfo
import com.skosc.tkffintech.misc.model.UpdateResult
import io.reactivex.Observable
import io.reactivex.Single

interface CurrentUserRepo {
    fun login(email: String, password: String): Single<Boolean>
    fun signout()
    val isLoggedIn: Observable<Boolean>
    val info: Observable<UserInfo>
    val id: Observable<Long>
    val idBlocking: Long?

    fun update(userInfo: UserInfo): Single<UpdateResult>
    fun forceRefreshUserInfo()
}