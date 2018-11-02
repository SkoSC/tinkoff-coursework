package com.skosc.tkffintech.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.utils.observeOnMainThread
import com.skosc.tkffintech.viewmodel.RxViewModel
import java.util.concurrent.TimeUnit

class LoginViewModelDemoImpl(private val userRepo: CurrentUserRepo) : LoginViewModel() {
    override val status: MutableLiveData<LoginStatus> = MutableLiveData()

    init {
        status.value = LoginStatus.WAITING
    }

    override fun login(email: String, password: String) {
        status.value = LoginStatus.IN_PROGRESS
        userRepo.login(email, password)
                .observeOnMainThread()
                .subscribe({ success ->
                    status.value = LoginStatus.SUCCESS
                }, { err ->
                    status.value = LoginStatus.ERROR
                })
    }
}