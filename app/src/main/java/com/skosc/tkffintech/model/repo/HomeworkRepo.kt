package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkGrade
import io.reactivex.Observable


interface HomeworkRepo {
    val statisticsScore: Observable<Double>
    val statisticsTestsCompleted: Observable<Int>
    val statisticsCurses: Observable<Int>

    fun update()

    fun homeworks(course: String) : Observable<List<Homework>>
    fun gradesForUserByTask(user: Long, task: Long): Observable<HomeworkGrade>
}