package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import com.skosc.tkffintech.model.webservice.TinkoffGradesApi
import com.skosc.tkffintech.usecase.UpdateGradesInfo
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject

class HomeworkRepoImpl(
        private val cursesApi: TinkoffCursesApi,
        private val gradesApi: TinkoffGradesApi,
        private val updateGradesInfo: UpdateGradesInfo
) : HomeworkRepo {
    override val statisticsScore: BehaviorSubject<Double> = BehaviorSubject.create()
    override val statisticsTestsCompleted: BehaviorSubject<Int> = BehaviorSubject.create()
    override val statisticsCurses: BehaviorSubject<Int> = BehaviorSubject.create()


    override fun update() {
        data class PR(val hw: TinkoffCursesApi.HomeworksResp, val gr: List<TinkoffGradesApi.GradesResp>)
        val curse = "android_fall2018"
        val zippper = BiFunction<TinkoffCursesApi.HomeworksResp, List<TinkoffGradesApi.GradesResp>, PR> { hw, gr ->
            PR(hw, gr)
        }



        Single.zip(cursesApi.homeworks(curse), gradesApi.gradesForCourse(curse), zippper)
                .subscribe { it ->
                    val homewroks = it.hw.homeworks.map { it.convert(curse) }
                    val grades = it.gr.flatMap { it.grades() }
                    homewroks.forEach {
                        val tasksIdSet = it.tasks.map { it.id }.toSet()
                        updateGradesInfo.update(grades.filter { it.taskId in tasksIdSet }, it)
                    }
                }
    }

    override fun homeworks(course: String): Observable<List<Homework>> {
        return cursesApi.homeworks(course).map { it.homeworks.map { it.convert(course) } }.toObservable()
    }
}