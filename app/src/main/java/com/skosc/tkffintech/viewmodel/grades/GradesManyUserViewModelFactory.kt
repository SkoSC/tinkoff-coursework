package com.skosc.tkffintech.viewmodel.grades

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import com.skosc.tkffintech.viewmodel.ViewModelArgs
import org.kodein.di.Kodein

class GradesManyUserViewModelFactory(kodein: Kodein, private val args: ViewModelArgs) : TKFViewModelFactory<GradesManyUserViewModel>(kodein) {
    override fun create(): GradesManyUserViewModel {
        return GradesManyUserViewModelImpl(args["course"]!!, inject())
    }
}