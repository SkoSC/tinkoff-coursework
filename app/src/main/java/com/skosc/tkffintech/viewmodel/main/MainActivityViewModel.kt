package com.skosc.tkffintech.viewmodel.main

import com.skosc.tkffintech.misc.Trigger
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class MainActivityViewModel : RxViewModel() {
    abstract val navigateToSplash: Trigger
}