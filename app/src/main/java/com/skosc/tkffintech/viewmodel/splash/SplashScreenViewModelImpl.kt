package com.skosc.tkffintech.viewmodel.splash

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.usecase.IsCurrentUserLoggedIn
import com.skosc.tkffintech.utils.extensions.own
import io.reactivex.android.schedulers.AndroidSchedulers

class SplashScreenViewModelImpl(private val checkForLogin: IsCurrentUserLoggedIn) : SplashScreenViewModel() {
    override val isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    init {
        cdisp own checkForLogin.isLoggedIn
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    isLoggedIn.value = it
                }
    }
}