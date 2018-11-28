package com.skosc.tkffintech.viewmodel.coursedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.R
import com.skosc.tkffintech.entities.composite.HomeworkWithGrades
import com.skosc.tkffintech.misc.model.UpdateResult
import com.skosc.tkffintech.usecase.LoadCourseStatistics
import com.skosc.tkffintech.usecase.LoadHomeworks
import com.skosc.tkffintech.utils.extensions.own
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CourseDetailViewModelImpl(
        private val course: String,
        private val loadHomeworks: LoadHomeworks,
        private val courseStatistics: LoadCourseStatistics
) : CourseDetailViewModel() {
    override val grades: MutableLiveData<List<HomeworkWithGrades>> = MutableLiveData()
    override val statsScore: MutableLiveData<Double> = MutableLiveData()
    override val statsTests: MutableLiveData<Int> = MutableLiveData()
    override val statsHomeWorks: MutableLiveData<Int> = MutableLiveData()
    override val topIcon: MutableLiveData<Int?> = MutableLiveData()

    override fun checkForUpdates(): LiveData<UpdateResult> {
        val indicator = MutableLiveData<UpdateResult>()
        cdisp own loadHomeworks.checkForUpdates(course)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ success ->
                    indicator.value = success
                    pushGrades()
                }, {
                    indicator.value = UpdateResult.Error
                })
        return indicator
    }

    override fun forceRefresh(): LiveData<UpdateResult> {
        val indicator = MutableLiveData<UpdateResult>()
        cdisp own loadHomeworks.tryLoadHomewroksFromNetwork(course)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ success ->
                    indicator.value = success
                    pushGrades()
                }, {
                    indicator.value = UpdateResult.Error
                })
        return indicator
    }

    private fun pushGrades() {
        cdisp own loadHomeworks.gradesForCurrentUser(course)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { freshGrades ->
                    grades.value = freshGrades
                    val stats = courseStatistics.calculateStatistics(freshGrades)
                    statsHomeWorks.value = stats.homeworkRatio.max.toInt()
                    statsTests.value = stats.testRatio.max.toInt()
                    statsScore.value = stats.scoreRatio.actual

                    if (stats.scoreRatio.asDouble() >= 0.5) {
                        topIcon.value = R.drawable.ic_smiling
                    } else {
                        topIcon.value = R.drawable.ic_sad
                    }

                }
    }
}