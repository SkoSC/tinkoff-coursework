package com.skosc.tkffintech.viewmodel.splash

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class SplashScreenViewModel : RxViewModel() {
    abstract val isLoggedIn: LiveData<Boolean>
}