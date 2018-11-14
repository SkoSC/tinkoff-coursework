package com.skosc.tkffintech.viewmodel.events

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.usecase.IsCurrentUserLoggedIn
import com.skosc.tkffintech.utils.own

class MainActivityViewModelImpl(private val checkForLogin: IsCurrentUserLoggedIn) : MainActivityViewModel() {
    override val isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    init {
        cdisp own checkForLogin.isLoggedIn.subscribe { it ->
            isLoggedIn.postValue(it)
        }
    }
}