package com.skosc.tkffintech.viewmodel.courses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.entities.CourseStatistics
import com.skosc.tkffintech.entities.composite.CourseWithStatistics
import com.skosc.tkffintech.misc.Ratio
import com.skosc.tkffintech.misc.UpdateResult
import com.skosc.tkffintech.usecase.LoadCourses
import com.skosc.tkffintech.utils.extensions.own
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CourseViewModelImpl(private val loadCourses: LoadCourses) : CourseViewModel() {

    override val activeCourses: MutableLiveData<List<CourseWithStatistics>> = MutableLiveData()
    override val allCourses: MutableLiveData<List<CourseInfo>> = MutableLiveData()

    init {

    }

    override fun forceUpdate(): LiveData<UpdateResult> {
        val indicator = MutableLiveData<UpdateResult>()
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

    override fun checkForUpdate(): LiveData<UpdateResult> {
        val indicator = MutableLiveData<UpdateResult>()
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
                                info = course,
                                statistics = CourseStatistics(Ratio(), Ratio(), Ratio(), Ratio())
                        )
                    }
                    allCourses.value = courses
                }
    }
}