package com.skosc.tkffintech.viewmodel.grades

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.entities.composite.HomeworkWithGrades
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class GradesSingleUserViewModel : RxViewModel() {
    companion object {
        const val ARG_COURSE = "course"
    }

    abstract val grades: LiveData<List<HomeworkWithGrades>>
    abstract val users: LiveData<List<User>>

    abstract fun setUser(user: User)
}