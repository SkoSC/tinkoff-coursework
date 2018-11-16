package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.viewmodel.UserWithGradesSum
import io.reactivex.Observable


interface HomeworkRepo {
    val statisticsScore: Observable<Double>
    val statisticsTestsCompleted: Observable<Int>
    val statisticsCurses: Observable<Int>

    fun update()

    fun homeworks(course: String) : Observable<List<Homework>>
    fun countAllCourses(): Observable<Int>

    fun gradesForUserByTask(user: Long, task: Long): Observable<HomeworkGrade>
    fun gradesTotalForAllUsersWithCourse(course: String): Observable<List<UserWithGradesSum>>
    fun gradesSumForUser(user: Long): Observable<Double>
    fun gradesForUser(user: Long): Observable<List<HomeworkGrade>>
    fun testGradesForUser(user: Long): Observable<List<HomeworkGrade>>
}