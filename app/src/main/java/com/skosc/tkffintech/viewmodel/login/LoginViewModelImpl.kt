package com.skosc.tkffintech.viewmodel.login

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.model.repo.NetworkErrors
import com.skosc.tkffintech.utils.observeOnMainThread
import com.skosc.tkffintech.utils.own

class LoginViewModelImpl(private val userRepo: CurrentUserRepo) : LoginViewModel() {
    override val status: MutableLiveData<Status> = MutableLiveData()

    init {
        status.value = LoginViewModel.Status.Waiting
    }

    override fun login(email: String, password: String) {
        status.value = LoginViewModel.Status.InProgress
        val cleanEmail = email.trim()
        val cleanPassword = password.trim()
        cdisp own userRepo.login(cleanEmail, cleanPassword)
                .observeOnMainThread()
                .subscribe(this::onLoginPassed, this::onLoginFailed)
    }

    private fun onLoginFailed(err: Throwable) {
        val loginError = when (err.message) {
            NetworkErrors.WRONG_CREDENTIALS -> LoginError.WRONG_CREDENTIALS
            else -> LoginError.UNKNOWN
        }
        status.value = LoginViewModel.Status.Error(loginError)
    }


    private fun onLoginPassed(success: Boolean) {
        if (success) {
            status.value = LoginViewModel.Status.Success
        } else {
            status.value = LoginViewModel.Status.Error(LoginError.WRONG_CREDENTIALS)
        }
    }
}