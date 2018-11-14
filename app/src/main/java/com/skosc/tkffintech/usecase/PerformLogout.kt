package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.repo.CurrentUserRepo

class PerformLogout(private val userRepo: CurrentUserRepo, private val logoutBomb: LogoutBomb) {
    fun perform() {
        userRepo.signout()
        logoutBomb.perform()
    }
}