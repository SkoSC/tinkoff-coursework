package com.skosc.tkffintech.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.model.repo.NetworkErrors
import com.skosc.tkffintech.utils.observeOnMainThread
import com.skosc.tkffintech.utils.own
import com.skosc.tkffintech.viewmodel.RxViewModel
import java.util.concurrent.TimeUnit

class LoginViewModelDemoImpl(private val userRepo: CurrentUserRepo) : LoginViewModel() {
    override val status: MutableLiveData<Status> = MutableLiveData()

    init {
        status.value = Status(LoginStatus.WAITING)
    }

    override fun login(email: String, password: String) {
        status.value = Status(LoginStatus.IN_PROGRESS)
        cdisp own userRepo.login(email, password)
                .observeOnMainThread()
                .subscribe({ success ->
                    status.value = Status(LoginStatus.SUCCESS)
                }, { err ->
                    val loginError = when (err.message) {
                        NetworkErrors.WRONG_CREDENTIALS -> LoginError.WRONG_CREDENTIALS
                        else -> LoginError.UNKNOWN
                    }

                    status.value = Status(LoginStatus.ERROR, loginError)
                })
    }
}