package com.skosc.tkffintech.viewmodel.main

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.usecase.IsCurrentUserLoggedIn
import com.skosc.tkffintech.utils.extensions.own
import io.reactivex.android.schedulers.AndroidSchedulers

class MainActivityViewModelImpl(private val checkForLogin: IsCurrentUserLoggedIn) : MainActivityViewModel() {
    override val isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    init {
        cdisp own checkForLogin.isLoggedIn
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    isLoggedIn.value = it
                }
    }
}