package com.skosc.tkffintech.viewmodel.courses

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.viewmodel.CourseWithStatistics

class CourseViewModelImpl : CourseViewModel() {

    override val activeCourses: MutableLiveData<List<CourseWithStatistics>> = MutableLiveData()
    override val allCourses: MutableLiveData<List<CourseInfo>> = MutableLiveData()

    init {

    }

    override fun forceUpdate() {
    }

    override fun checkForUpdate() {
    }
}