package com.skosc.tkffintech.viewmodel.grades

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.viewmodel.HomeworkWithGrades
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class GradesSingleUserViewModel : RxViewModel() {
    abstract val grades: LiveData<List<HomeworkWithGrades>>
    abstract val users: LiveData<List<User>>

    abstract fun init(course: String)
    abstract fun setUser(user: User)
}