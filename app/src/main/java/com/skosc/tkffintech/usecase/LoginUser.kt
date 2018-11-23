package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.repo.CurrentUserRepo

class LoginUser(private val currentUserRepo: CurrentUserRepo) {
    fun login(email: String, password: String) = currentUserRepo.login(email, password)
}