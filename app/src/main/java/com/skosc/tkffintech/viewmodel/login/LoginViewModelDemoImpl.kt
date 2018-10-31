package com.skosc.tkffintech.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.viewmodel.RxViewModel

class LoginViewModelDemoImpl : LoginViewModel() {
    override val status: MutableLiveData<LoginStatus> = MutableLiveData()

    init {
        status.value = LoginStatus.IN_PROGRESS
    }

    override fun login(email: String, password: String) {
        status.value = LoginStatus.SUCCESS
    }
}