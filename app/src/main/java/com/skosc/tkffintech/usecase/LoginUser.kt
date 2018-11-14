package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.repo.CurrentUserRepo

class LoginUser(private val userRepo: CurrentUserRepo) {
    fun login(email: String, password: String) = userRepo.login(email, password)

}