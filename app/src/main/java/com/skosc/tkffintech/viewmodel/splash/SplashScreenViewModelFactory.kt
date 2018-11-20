package com.skosc.tkffintech.viewmodel.splash

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import org.kodein.di.Kodein

class SplashScreenViewModelFactory(kodein: Kodein) : TKFViewModelFactory<SplashScreenViewModel>(kodein) {
    override fun create(): SplashScreenViewModel {
        return SplashScreenViewModelImpl(inject())
    }
}