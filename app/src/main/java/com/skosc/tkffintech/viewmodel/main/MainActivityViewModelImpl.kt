package com.skosc.tkffintech.viewmodel.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.model.repo.EventsRepo
import com.skosc.tkffintech.utils.observeOnMainThread
import com.skosc.tkffintech.utils.own

class MainActivityViewModelImpl(private val currentUserRepo: CurrentUserRepo) : MainActivityViewModel() {
    override val isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    init {
        cdisp own currentUserRepo.isLoggedIn.subscribe { it ->
            isLoggedIn.postValue(it)
        }
    }
}