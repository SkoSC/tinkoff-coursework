package com.skosc.tkffintech.viewmodel.main

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class MainActivityViewModel : RxViewModel() {
    abstract val isLoggedIn: LiveData<Boolean>
}