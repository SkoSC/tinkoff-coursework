package com.skosc.tkffintech.viewmodel.coursedetail

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.usecase.LoadGrades
import com.skosc.tkffintech.usecase.CourseStatisticsCalculatorForCurrentUser
import com.skosc.tkffintech.utils.own
import com.skosc.tkffintech.viewmodel.HomeworkWithGrades
import io.reactivex.android.schedulers.AndroidSchedulers

class CourseDetailViewModelImpl(
        private val course: String,
        private val loader: LoadGrades,
        private val statisticsCalculatorForCourse: CourseStatisticsCalculatorForCurrentUser
) : CourseDetailViewModel() {

    override val grades: MutableLiveData<List<HomeworkWithGrades>> = MutableLiveData()
    override val statsScore: MutableLiveData<Double> = MutableLiveData()
    override val statsTests: MutableLiveData<Int> = MutableLiveData()
    override val statsHomeWorks: MutableLiveData<Int> = MutableLiveData()

    init {
        cdisp own loader.loadGradesForCurrentUser(course).map {
            it.map { HomeworkWithGrades(it.first, it.second.map { it.task to it.grade }) }
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    grades.value = it
                }


        cdisp own statisticsCalculatorForCourse.totalScore(course)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    statsScore.value = it
                }

        cdisp own statisticsCalculatorForCourse.totalTests(course)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    statsTests.value = it
                }

        cdisp own statisticsCalculatorForCourse.totalHomeworks(course)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    statsHomeWorks.value = it
                }
    }
}