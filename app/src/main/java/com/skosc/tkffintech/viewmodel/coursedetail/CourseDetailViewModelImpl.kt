package com.skosc.tkffintech.viewmodel.coursedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.misc.DataUpdateResult
import com.skosc.tkffintech.usecase.LoadHomeworks
import com.skosc.tkffintech.utils.own
import com.skosc.tkffintech.viewmodel.HomeworkWithGrades
import io.reactivex.android.schedulers.AndroidSchedulers

class CourseDetailViewModelImpl(
        private val course: String,
        private val loadHomeworks: LoadHomeworks
) : CourseDetailViewModel() {
    override val grades: MutableLiveData<List<HomeworkWithGrades>> = MutableLiveData()
    override val statsScore: MutableLiveData<Double> = MutableLiveData()
    override val statsTests: MutableLiveData<Int> = MutableLiveData()
    override val statsHomeWorks: MutableLiveData<Int> = MutableLiveData()

    init {

    }

    override fun checkForUpdates(): LiveData<DataUpdateResult> {
        val indicator = MutableLiveData<DataUpdateResult>()
        cdisp own loadHomeworks.checkForUpdates(course)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { success ->
                    indicator.value = success
                }
        return indicator
    }

    override fun forceReload(): LiveData<DataUpdateResult> {
        val indicator = MutableLiveData<DataUpdateResult>()
        cdisp own loadHomeworks.tryLoadHomewroksFromNetwork(course)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { success ->
                    indicator.value = success
                }
        return indicator
    }

}