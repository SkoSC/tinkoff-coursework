package com.skosc.tkffintech.viewmodel.coursedetail

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import com.skosc.tkffintech.viewmodel.ViewModelArgs
import org.kodein.di.Kodein

class CourseDetailViewModelFactory(kodein: Kodein, private val args: ViewModelArgs)
    : TKFViewModelFactory<CourseDetailViewModel>(kodein) {

    private val course
        get() = args[CourseDetailViewModel.COURSE_ARG]
                ?: throw IllegalStateException("No argument: 'course' passed to factory")

    override fun create(): CourseDetailViewModel {
        return CourseDetailViewModelImpl(course, inject())
    }
}