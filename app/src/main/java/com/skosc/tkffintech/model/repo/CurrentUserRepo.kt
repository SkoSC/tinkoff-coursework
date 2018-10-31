package com.skosc.tkffintech.model.repo

import io.reactivex.Single

interface CurrentUserRepo {
    fun login(email: String, password: String): Single<Boolean>
}