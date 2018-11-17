package com.skosc.tkffintech.viewmodel.main

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import com.skosc.tkffintech.viewmodel.events.MainActivityViewModel
import com.skosc.tkffintech.viewmodel.events.MainActivityViewModelImpl
import org.kodein.di.Kodein

class MainActivityViewModelFactory(kodein: Kodein) : TKFViewModelFactory<MainActivityViewModel>(kodein) {
    override fun create(): MainActivityViewModel {
        return MainActivityViewModelImpl(inject())
    }
}