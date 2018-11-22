package com.skosc.tkffintech.viewmodel.courses

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.misc.Ratio
import com.skosc.tkffintech.usecase.CourseStatisticsCalculator
import com.skosc.tkffintech.usecase.LoadCourses
import com.skosc.tkffintech.utils.mapEach
import com.skosc.tkffintech.utils.own
import com.skosc.tkffintech.viewmodel.CourseStatistics
import com.skosc.tkffintech.viewmodel.CourseWithStatistics
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CourseViewModelImpl(
        private val loadCourses: LoadCourses,
        private val statistics: CourseStatisticsCalculator
) : CourseViewModel() {

    override val activeCourses: MutableLiveData<List<CourseWithStatistics>> = MutableLiveData()
    override val allCourses: MutableLiveData<List<CourseInfo>> = MutableLiveData()

    init {
        loadCourses.checkForUpdates()

        cdisp own loadCourses.allCourses
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    allCourses.value = it
                }
        cdisp own loadCourses.allCourses
                .subscribeOn(Schedulers.io())
                .map { statistics.bundled(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    activeCourses.value = it
                }
    }

    override fun forceUpdate() {
        loadCourses.tryLoadCourses()
    }

    override fun checkForUpdate() {
        loadCourses.checkForUpdates()
    }
}