package com.skosc.tkffintech.viewmodel.login

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import com.skosc.tkffintech.misc.Trigger
import com.skosc.tkffintech.viewmodel.RxViewModel


abstract class LoginViewModel : RxViewModel() {
    enum class LoginError { UNKNOWN, WRONG_CREDENTIALS }
    data class Error(val error: LoginError, @StringRes val text: Int)

    abstract fun login(email: String, password: String)

    abstract val dataIsLoading: LiveData<Boolean>
    abstract val error: LiveData<Error?>
    abstract val navigateToMain: Trigger
}