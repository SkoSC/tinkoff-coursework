package com.skosc.tkffintech.viewmodel.coursedetail

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import org.kodein.di.Kodein

class CourseDetailViewModelFactory(kodein: Kodein) : TKFViewModelFactory<CourseDetailViewModel>(kodein) {
    override fun create(): CourseDetailViewModel {
        return CourseDetailViewModelImpl(inject(), inject())
    }
}