package com.skosc.tkffintech.viewmodel.login

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class LoginViewModel : RxViewModel() {
    enum class LoginStatus { WAITING, ERROR, IN_PROGRESS, SUCCESS }
    enum class LoginError { UNKNOWN, WRONG_CREDENTIALS }
    data class Status(val login: LoginStatus, val error: LoginError? = null)

    abstract val status: LiveData<Status>
    abstract fun login(email: String, password: String)
}