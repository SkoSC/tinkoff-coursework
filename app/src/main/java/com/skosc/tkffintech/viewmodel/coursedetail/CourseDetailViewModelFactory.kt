package com.skosc.tkffintech.viewmodel.coursedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.instance

class CourseDetailViewModelFactory(private val kodein: Kodein) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CourseDetailViewModelImpl(kodein.direct.instance(), kodein.direct.instance()) as T
    }
}