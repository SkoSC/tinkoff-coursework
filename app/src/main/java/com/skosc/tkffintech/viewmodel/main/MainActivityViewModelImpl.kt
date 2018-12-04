package com.skosc.tkffintech.viewmodel.main

import com.skosc.tkffintech.misc.Trigger
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.utils.extensions.own
import io.reactivex.android.schedulers.AndroidSchedulers

class MainActivityViewModelImpl(currentUserRepo: CurrentUserRepo) : MainActivityViewModel() {
    override val navigateToSplash: Trigger = Trigger()

    init {
        cdisp own currentUserRepo.isLoggedIn
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isLoggedIn ->
                    if (!isLoggedIn) {
                        navigateToSplash.fire()
                    }
                }
    }
}