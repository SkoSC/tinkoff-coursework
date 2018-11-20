package com.skosc.tkffintech.viewmodel.main

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import org.kodein.di.Kodein

class MainActivityViewModelFactory(kodein: Kodein) : TKFViewModelFactory<MainActivityViewModel>(kodein) {
    override fun create(): MainActivityViewModel {
        return MainActivityViewModelImpl(inject())
    }
}