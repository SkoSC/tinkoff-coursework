package com.skosc.tkffintech.viewmodel.grades

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import com.skosc.tkffintech.viewmodel.ViewModelArgs
import org.kodein.di.Kodein

class GradesSingleUserViewModelFactory(kodein: Kodein, private val args: ViewModelArgs) : TKFViewModelFactory<GradesSingleUserViewModel>(kodein) {
    override fun create(): GradesSingleUserViewModel {
        return GradesSingleUserViewModelImpl(args["course"]!!, inject(), inject())
    }
}