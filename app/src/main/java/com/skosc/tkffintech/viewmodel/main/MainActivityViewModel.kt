package com.skosc.tkffintech.viewmodel.events

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class MainActivityViewModel : RxViewModel() {
    abstract val isLoggedIn: LiveData<Boolean>
}