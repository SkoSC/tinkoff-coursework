package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.model.room.CourseInfoDao
import com.skosc.tkffintech.model.room.allBusiness
import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import com.skosc.tkffintech.usecase.UpdateCourseList
import com.skosc.tkffintech.usecase.UpdateGradesInfo
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class HomeworkRepoImpl(
        private val cursesApi: TinkoffCursesApi,
        private val courseInfoDao: CourseInfoDao,
        private val updateCourseList: UpdateCourseList,
        private val updateGradesInfo: UpdateGradesInfo
) : HomeworkRepo {
    override val statisticsScore: BehaviorSubject<Double> = BehaviorSubject.create()
    override val statisticsTestsCompleted: BehaviorSubject<Int> = BehaviorSubject.create()
    override val statisticsCurses: BehaviorSubject<Int> = BehaviorSubject.create()


    override fun update() {
        updateCourseList.perform()
        courseInfoDao.allBusiness().subscribe {
            it.map {
                updateGradesInfo.perform(it.url)
            }
        }


    }

    override fun homeworks(course: String): Observable<List<Homework>> {
        return cursesApi.homeworks(course).map { it.homeworks.map { it.convert(course) } }.toObservable()
    }
}