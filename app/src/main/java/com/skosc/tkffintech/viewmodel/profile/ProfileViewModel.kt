package com.skosc.tkffintech.viewmodel.profile

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class ProfileViewModel : RxViewModel() {
    abstract val fullName: LiveData<String>
    abstract val shortInfo: LiveData<String>
    abstract val statsScore: LiveData<Double>
    abstract val statsTests: LiveData<Int>
    abstract val statsCourses: LiveData<Int>
    abstract val quote: LiveData<String>

    abstract val contactInfo: LiveData<Map<Int, String>>
    abstract val schoolInfo: LiveData<Map<Int, String>>
    abstract val workInfo: LiveData<Map<Int, String>>

    abstract fun signout()
    abstract fun refresh()
}