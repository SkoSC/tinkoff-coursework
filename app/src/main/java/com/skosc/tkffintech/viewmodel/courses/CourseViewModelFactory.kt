package com.skosc.tkffintech.viewmodel.courses

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import org.kodein.di.Kodein

class CourseViewModelFactory(kodein: Kodein) : TKFViewModelFactory<CourseViewModel>(kodein) {
    override fun create(): CourseViewModel {
        return CourseViewModelImpl(inject(), inject())
    }
}