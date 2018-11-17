package com.skosc.tkffintech.viewmodel.grades

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import org.kodein.di.Kodein

class GradesSingleUserViewModelFactory(kodein: Kodein) : TKFViewModelFactory<GradesSingleUserViewModel>(kodein) {
    override fun create(): GradesSingleUserViewModel {
        return GradesSingleUserViewModelImpl(inject(), inject())
    }
}