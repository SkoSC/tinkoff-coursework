package com.skosc.tkffintech.viewmodel.main

import com.skosc.tkffintech.misc.Trigger
import com.skosc.tkffintech.usecase.IsCurrentUserLoggedIn
import com.skosc.tkffintech.utils.extensions.own
import io.reactivex.android.schedulers.AndroidSchedulers

class MainActivityViewModelImpl(checkForLogin: IsCurrentUserLoggedIn) : MainActivityViewModel() {
    override val navigateToSplash: Trigger = Trigger()

    init {
        cdisp own checkForLogin.isLoggedIn
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isLoggedIn ->
                    if (!isLoggedIn) {
                        navigateToSplash.fire()
                    }
                }
    }
}