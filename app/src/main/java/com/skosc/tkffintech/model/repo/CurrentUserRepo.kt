package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.UserInfo
import com.skosc.tkffintech.misc.model.UpdateResult
import io.reactivex.Observable
import io.reactivex.Single

interface CurrentUserRepo {

    /**
     * Loggins current user
     */
    fun login(email: String, password: String): Single<Boolean>

    /**
     * Logouts current user
     */
    fun signout()

    /**
     * Checks if current user is logged in
     */
    val isLoggedIn: Observable<Boolean>

    /**
     * Information about current user info
     */
    val info: Observable<UserInfo>

    /**
     * Observable containing just [UserInfo.id]
     */
    val id: Observable<Long>

    /**
     * Blocking version of [CurrentUserRepo.id]
     */
    val idBlocking: Long?

    /**
     * Update user info on server
     */
    fun update(userInfo: UserInfo): Single<UpdateResult>

    /**
     * Load current user info from server
     */
    fun forceRefreshUserInfo()
}