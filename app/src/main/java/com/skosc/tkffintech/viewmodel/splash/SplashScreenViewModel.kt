package com.skosc.tkffintech.viewmodel.splash

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.misc.Trigger
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class SplashScreenViewModel : RxViewModel() {
    abstract val navigateToMain: Trigger
    abstract val navigateToLogin: Trigger
}