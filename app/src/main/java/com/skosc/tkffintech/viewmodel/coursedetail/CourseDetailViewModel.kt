package com.skosc.tkffintech.viewmodel.coursedetail

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.entities.HomeworkTask
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class CourseDetailViewModel : RxViewModel() {
    data class HomeworkWithGrades(val homework: Homework, val grades: List<Pair<HomeworkTask, HomeworkGrade>>)
    abstract val grades: LiveData<List<HomeworkWithGrades>>
}