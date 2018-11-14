package com.skosc.tkffintech.viewmodel.courses

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class CourseViewModel : RxViewModel() {
    abstract val activeCourses: LiveData<CourseInfo>
    abstract val allCourses: LiveData<List<CourseInfo>>
}