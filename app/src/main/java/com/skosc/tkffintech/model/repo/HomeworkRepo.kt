package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.usecase.TaskWithGrade
import com.skosc.tkffintech.viewmodel.UserWithGradesSum
import io.reactivex.Observable


interface HomeworkRepo {
    fun tryForceUpdate()

    fun homeworks(course: String): Observable<List<Homework>>
    fun countAllCourses(): Observable<Int>

    fun gradesForUserByTask(user: Long, task: Long): Observable<HomeworkGrade>
    fun gradesTotalForAllUsersWithCourse(course: String): Observable<List<UserWithGradesSum>>
    fun gradesSumForUser(user: Long): Observable<Double>
    fun gradesForUser(user: Long): Observable<List<HomeworkGrade>>
    fun testGradesForUser(user: Long): Observable<List<HomeworkGrade>>
    fun performSoftUpdate()

    fun gradesWithHomework(user: Long, course: String): Observable<List<Pair<Homework, List<TaskWithGrade>>>>
}