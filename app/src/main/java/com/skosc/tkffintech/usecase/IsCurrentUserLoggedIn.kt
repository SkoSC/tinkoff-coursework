package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.dao.UserInfoDao
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import io.reactivex.Observable

class IsCurrentUserLoggedIn(private val currentUserRepo: CurrentUserRepo) {
    val isLoggedIn: Observable<Boolean> = currentUserRepo.isLoggedIn
}