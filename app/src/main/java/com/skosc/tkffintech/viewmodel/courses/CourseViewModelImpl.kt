package com.skosc.tkffintech.viewmodel.courses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.entities.CourseStatistics
import com.skosc.tkffintech.entities.composite.CourseWithStatistics
import com.skosc.tkffintech.misc.model.Ratio
import com.skosc.tkffintech.misc.model.UpdateResult
import com.skosc.tkffintech.model.repo.CourseRepo
import com.skosc.tkffintech.usecase.LoadCourseStatistics
import com.skosc.tkffintech.utils.extensions.own
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CourseViewModelImpl(private val coursesRepo: CourseRepo, private val statistics: LoadCourseStatistics) : CourseViewModel() {

    override val activeCourses: MutableLiveData<List<CourseWithStatistics>> = MutableLiveData()
    override val allCourses: MutableLiveData<List<CourseInfo>> = MutableLiveData()

    init {

    }

    override fun forceUpdate(): LiveData<UpdateResult> {
        val indicator = MutableLiveData<UpdateResult>()
        cdisp own coursesRepo.tryForceRefresh()
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
        cdisp own coursesRepo.checkForUpdates()
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
        cdisp own coursesRepo.courses
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ courses ->
                    allCourses.value = courses
                }, {

                })

        cdisp own coursesRepo.courses
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { courses ->
                    activeCourses.value = courses.map { course ->
                        CourseWithStatistics(
                                info = course,
                                statistics = CourseStatistics(Ratio(), Ratio(), Ratio(), Ratio())
                        )
                    }

                    courses.map {
                        CourseWithStatistics(
                                info = it,
                                statistics = statistics.bundled(it.url)
                                        .subscribeOn(Schedulers.io())
                                        .blockingGet()
                        )
                    }
                }.subscribe ({ courses ->
                    activeCourses.value = courses
                }, {})
    }
}