package com.skosc.tkffintech.viewmodel.coursedetail

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.entities.composite.HomeworkWithGrades
import com.skosc.tkffintech.misc.model.UpdateResult
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class CourseDetailViewModel : RxViewModel() {
    companion object Args {
        const val COURSE_ARG = "course"
    }

    abstract val grades: LiveData<List<HomeworkWithGrades>>
    abstract val statsScore: LiveData<Double>
    abstract val statsTests: LiveData<Int>
    abstract val statsHomeWorks: LiveData<Int>
    abstract val topIcon: LiveData<Int?>
    abstract fun checkForUpdates(): LiveData<UpdateResult>
    abstract fun forceRefresh(): LiveData<UpdateResult>
}