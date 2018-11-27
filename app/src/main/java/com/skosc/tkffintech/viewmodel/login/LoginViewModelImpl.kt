package com.skosc.tkffintech.viewmodel.login

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.R
import com.skosc.tkffintech.misc.Trigger
import com.skosc.tkffintech.model.repo.NetworkErrors
import com.skosc.tkffintech.usecase.LoginUser
import com.skosc.tkffintech.utils.extensions.observeOnMainThread
import com.skosc.tkffintech.utils.extensions.own

class LoginViewModelImpl(private val loginUser: LoginUser) : LoginViewModel() {
    override val error: MutableLiveData<Error?> = MutableLiveData()
    override val dataIsLoading: MutableLiveData<Boolean> = MutableLiveData()
    override val navigateToMain: Trigger = Trigger()

    init {
        dataIsLoading.value = false
    }

    override fun login(email: String, password: String) {
        dataIsLoading.value = true

        val cleanEmail = email.trim()
        val cleanPassword = password.trim()

        cdisp own loginUser.login(cleanEmail, cleanPassword)
                .observeOnMainThread()
                .subscribe(this::onLoginPassed, this::onLoginFailed)
    }

    private fun onLoginFailed(err: Throwable) {
        dataIsLoading.value = false
        error.value = extractErrorInfo(err)
    }

    private fun extractErrorInfo(err: Throwable): Error {
        return when (err.message) {
            NetworkErrors.WRONG_CREDENTIALS -> Error(LoginError.WRONG_CREDENTIALS, R.string.login_error_wrong_credentials)
            else -> Error(LoginError.UNKNOWN, R.string.login_error_wrong_unknown)
        }
    }


    private fun onLoginPassed(success: Boolean) {
        if (success) {
            navigateToMain.fire()
        } else {
            error.value = Error(LoginError.WRONG_CREDENTIALS, R.string.login_error_wrong_credentials)
        }
    }
}