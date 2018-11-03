package com.skosc.tkffintech.model.dao

import io.reactivex.Single

interface SecurityDao {
    fun getAuthCookie(): Single<String>
    fun setAuthCookie(cookie: String)
}