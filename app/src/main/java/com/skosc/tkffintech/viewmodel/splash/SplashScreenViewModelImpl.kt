package com.skosc.tkffintech.viewmodel.splash

import com.skosc.tkffintech.misc.Trigger
import com.skosc.tkffintech.usecase.IsCurrentUserLoggedIn
import com.skosc.tkffintech.utils.extensions.own
import io.reactivex.android.schedulers.AndroidSchedulers

class SplashScreenViewModelImpl(checkForLogin: IsCurrentUserLoggedIn) : SplashScreenViewModel() {
    override val navigateToMain: Trigger = Trigger()
    override val navigateToLogin: Trigger = Trigger()

    init {
        cdisp own checkForLogin.isLoggedIn
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isLoggedIn ->
                    if (isLoggedIn) {
                        navigateToMain.fire()
                    } else {
                        navigateToLogin.fire()
                    }
                }
    }
}