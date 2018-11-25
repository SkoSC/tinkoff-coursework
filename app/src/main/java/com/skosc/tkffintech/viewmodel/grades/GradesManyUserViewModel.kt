package com.skosc.tkffintech.viewmodel.grades

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.entities.composite.UserWithGradesSum
import com.skosc.tkffintech.misc.ItemSorter
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class GradesManyUserViewModel : RxViewModel() {
    companion object {
        const val ARG_COURSE = "course"
    }

    abstract val sorters: LiveData<List<ItemSorter<UserWithGradesSum>>>
    abstract val userWithGradesSum: LiveData<List<UserWithGradesSum>>
    abstract fun setSorter(sorter: ItemSorter<UserWithGradesSum>)
}