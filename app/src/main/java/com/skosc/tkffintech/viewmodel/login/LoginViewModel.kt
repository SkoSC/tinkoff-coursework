package com.skosc.tkffintech.viewmodel.login

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.viewmodel.RxViewModel





abstract class LoginViewModel : RxViewModel() {
    enum class LoginError { UNKNOWN, WRONG_CREDENTIALS }
    sealed class Status {
        object Waiting : Status()
        object InProgress : Status()
        object Success : Status()
        data class Error(val error: LoginError) : Status()
    }

    abstract val status: LiveData<Status>
    abstract fun login(email: String, password: String)
}