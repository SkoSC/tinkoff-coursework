package com.skosc.tkffintech.viewmodel.login

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class LoginViewModel : RxViewModel() {
    enum class LoginStatus { ERROR, IN_PROGRESS, SUCCESS }

    abstract val status: LiveData<LoginStatus>
    abstract fun login(email: String, password: String)
}