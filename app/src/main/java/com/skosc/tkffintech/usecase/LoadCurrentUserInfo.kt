package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.repo.CurrentUserRepo

class LoadCurrentUserInfo(private val currentUserRepo: CurrentUserRepo) {
    val currentUserInfo = currentUserRepo.info

    fun tryLoadUserInfoFromNetwork() {
        currentUserRepo.forceRefreshUserInfo()
    }
}