package com.skosc.tkffintech.viewmodel.grades

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import org.kodein.di.Kodein

class GradesManyUserViewModelFactory(kodein: Kodein) : TKFViewModelFactory<GradesManyUserViewModel>(kodein) {
    override fun create(): GradesManyUserViewModel {
        return GradesManyUserViewModelImpl(inject())
    }
}