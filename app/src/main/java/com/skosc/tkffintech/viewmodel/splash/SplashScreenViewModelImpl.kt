package com.skosc.tkffintech.viewmodel.splash

import com.skosc.tkffintech.misc.Trigger
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.utils.extensions.own
import io.reactivex.android.schedulers.AndroidSchedulers

class SplashScreenViewModelImpl(currentUserRepo: CurrentUserRepo) : SplashScreenViewModel() {
    override val navigateToMain: Trigger = Trigger()
    override val navigateToLogin: Trigger = Trigger()

    init {
        cdisp own currentUserRepo.isLoggedIn
                .firstOrError()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ isLoggedIn ->
                    if (isLoggedIn) {
                        navigateToMain.fire()
                    } else {
                        navigateToLogin.fire()
                    }
                }, {})
    }
}