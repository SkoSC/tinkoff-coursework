package com.skosc.tkffintech.viewmodel.courses

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.entities.composite.CourseWithStatistics
import com.skosc.tkffintech.misc.model.UpdateResult
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class CourseViewModel : RxViewModel() {
    abstract val activeCourses: LiveData<List<CourseWithStatistics>>
    abstract val allCourses: LiveData<List<CourseInfo>>
    abstract fun forceUpdate(): LiveData<UpdateResult>
    abstract fun checkForUpdate(): LiveData<UpdateResult>
}