package com.skosc.tkffintech.viewmodel.login

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.model.repo.NetworkErrors
import com.skosc.tkffintech.utils.observeOnMainThread
import com.skosc.tkffintech.utils.own

class LoginViewModelImpl(private val userRepo: CurrentUserRepo) : LoginViewModel() {
    override val status: MutableLiveData<Status> = MutableLiveData()

    init {
        status.value = Status(LoginStatus.WAITING)
    }

    override fun login(email: String, password: String) {
        status.value = Status(LoginStatus.IN_PROGRESS)
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
        status.value = Status(LoginStatus.ERROR, loginError)
    }


    private fun onLoginPassed(success: Boolean) {
        if (success) {
            status.value = Status(LoginStatus.SUCCESS)
        } else {
            status.value = Status(LoginStatus.ERROR, LoginError.WRONG_CREDENTIALS)
        }
    }
}