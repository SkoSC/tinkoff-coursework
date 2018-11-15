package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.repo.CurrentUserRepo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class PerformLogout(private val userRepo: CurrentUserRepo, private val logoutBomb: LogoutBomb) {
    fun perform() {
        Single.fromCallable {
            userRepo.signout()
            logoutBomb.perform()
        }.subscribeOn(Schedulers.io())
                .subscribe()

    }
}