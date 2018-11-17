package com.skosc.tkffintech.viewmodel.login

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import org.kodein.di.Kodein

class LoginViewModelFactory(kodein: Kodein) : TKFViewModelFactory<LoginViewModel>(kodein) {
    override fun create(): LoginViewModel {
        return LoginViewModelImpl(inject())
    }
}