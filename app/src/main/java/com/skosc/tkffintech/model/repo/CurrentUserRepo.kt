package com.skosc.tkffintech.model.repo

import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

interface CurrentUserRepo {
    fun login(email: String, password: String): Single<Boolean>
    val isLoggedIn: BehaviorSubject<Boolean>
}