package com.skosc.tkffintech.viewmodel.coursedetail

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class CourseDetailViewModel : RxViewModel() {
    abstract val homeworks: LiveData<List<Homework>>
}