package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.UserInfo
import com.skosc.tkffintech.misc.model.UpdateResult
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import io.reactivex.Single

class UpdateUserInfo(private val currentUserRepo: CurrentUserRepo) {
    fun update(userInfo: UserInfo): Single<UpdateResult> {
        return currentUserRepo.update(userInfo)
    }
}