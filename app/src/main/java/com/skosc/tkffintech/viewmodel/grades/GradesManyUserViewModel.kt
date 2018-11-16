package com.skosc.tkffintech.viewmodel.grades

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.utils.ItemSorter
import com.skosc.tkffintech.viewmodel.HomeworkWithGrades
import com.skosc.tkffintech.viewmodel.RxViewModel
import com.skosc.tkffintech.viewmodel.UserWithGradesSum

abstract class GradesManyUserViewModel : RxViewModel() {
    abstract val sorters: LiveData<List<ItemSorter<UserWithGradesSum>>>
    abstract val userWithGradesSum: LiveData<List<UserWithGradesSum>>
    abstract fun setSorter(sorter: ItemSorter<UserWithGradesSum>)
    abstract fun init(course: String)
}