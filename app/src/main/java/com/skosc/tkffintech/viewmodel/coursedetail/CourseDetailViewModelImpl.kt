package com.skosc.tkffintech.viewmodel.coursedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.misc.DataUpdateResult
import com.skosc.tkffintech.viewmodel.HomeworkWithGrades

class CourseDetailViewModelImpl(
        private val course: String
) : CourseDetailViewModel() {
    override val grades: MutableLiveData<List<HomeworkWithGrades>> = MutableLiveData()
    override val statsScore: MutableLiveData<Double> = MutableLiveData()
    override val statsTests: MutableLiveData<Int> = MutableLiveData()
    override val statsHomeWorks: MutableLiveData<Int> = MutableLiveData()

    init {

    }

    override fun checkForUpdates(): LiveData<DataUpdateResult> {
        return MutableLiveData()
    }

    override fun forceReload(): LiveData<DataUpdateResult> {
        return MutableLiveData()
    }

}