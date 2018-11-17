package com.skosc.tkffintech.viewmodel.coursedetail

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.viewmodel.HomeworkWithGrades
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class CourseDetailViewModel : RxViewModel() {
    abstract val grades: LiveData<List<HomeworkWithGrades>>
    abstract val statsScore: LiveData<Double>
    abstract val statsTests: LiveData<Int>
    abstract val statsHomeWorks: LiveData<Int>
}