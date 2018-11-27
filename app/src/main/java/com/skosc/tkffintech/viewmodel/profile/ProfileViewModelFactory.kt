package com.skosc.tkffintech.viewmodel.profile

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import org.kodein.di.Kodein

class ProfileViewModelFactory(kodein: Kodein) : TKFViewModelFactory<ProfileViewModel>(kodein) {
    override fun create(): ProfileViewModel {
        return ProfileViewModelImpl(inject(), inject(), inject(), inject())
    }
}