package com.skosc.tkffintech.viewmodel.courses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.misc.DataUpdateResult
import com.skosc.tkffintech.misc.Ratio
import com.skosc.tkffintech.usecase.LoadCourses
import com.skosc.tkffintech.utils.own
import com.skosc.tkffintech.viewmodel.CourseStatistics
import com.skosc.tkffintech.viewmodel.CourseWithStatistics
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CourseViewModelImpl(private val loadCourses: LoadCourses) : CourseViewModel() {

    override val activeCourses: MutableLiveData<List<CourseWithStatistics>> = MutableLiveData()
    override val allCourses: MutableLiveData<List<CourseInfo>> = MutableLiveData()

    init {

    }

    override fun forceUpdate(): LiveData<DataUpdateResult> {
        val indicator = MutableLiveData<DataUpdateResult>()
        cdisp own loadCourses.forceRefresh()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    pushData()
                }
                .subscribe { status ->
                    indicator.value = status
                }
        return indicator
    }

    override fun checkForUpdate(): LiveData<DataUpdateResult> {
        val indicator = MutableLiveData<DataUpdateResult>()
        cdisp own loadCourses.checkForUpdates()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    pushData()
                }
                .subscribe { status ->
                    indicator.value = status
                }
        return indicator
    }

    private fun pushData() {
        cdisp own loadCourses.courses
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { courses ->
                    activeCourses.value = courses.map { course ->
                        CourseWithStatistics(
                                info =course,
                                statistics = CourseStatistics(Ratio(), Ratio(), Ratio(), Ratio())
                        )
                    }
                    allCourses.value = courses
                }
    }
}