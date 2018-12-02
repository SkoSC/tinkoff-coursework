package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.repo.CurrentUserRepo
import io.reactivex.Observable

class IsCurrentUserLoggedIn(currentUserRepo: CurrentUserRepo) {
    val isLoggedIn: Observable<Boolean> = currentUserRepo.isLoggedIn
}