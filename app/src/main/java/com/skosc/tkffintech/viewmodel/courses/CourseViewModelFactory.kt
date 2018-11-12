package com.skosc.tkffintech.viewmodel.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.instance

class CourseViewModelFactory(private val kodein: Kodein) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CourseViewModelImpl(kodein.direct.instance(), kodein.direct.instance()) as T
    }
}