package com.skosc.tkffintech.model.dao

import io.reactivex.Single

interface SecurityDao {
    fun clearAuthCredentials()
    val hasAuthCredentials: Single<Boolean>
}