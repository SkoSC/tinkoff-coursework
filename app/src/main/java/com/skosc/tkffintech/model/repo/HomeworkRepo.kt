package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.usecase.TaskWithGrade
import com.skosc.tkffintech.viewmodel.UserWithGradesSum
import io.reactivex.Observable


interface HomeworkRepo {

    // Simple operations

    fun homeworks(course: String): Observable<List<Homework>>
    fun countAllCourses(): Observable<Int>

    // Complicated operations

    fun gradesForUserByTask(userId: Long, task: Long): Observable<HomeworkGrade>
    fun gradesTotalForAllUsersWithCourse(course: String): Observable<List<UserWithGradesSum>>
    fun gradesSumForUser(userId: Long): Observable<Double>
    fun gradesForUser(userId: Long): Observable<List<HomeworkGrade>>
    fun testGradesForUser(userId: Long): Observable<List<HomeworkGrade>>
    fun gradesWithHomework(user: Long, course: String): Observable<List<Pair<Homework, List<TaskWithGrade>>>>

    // Updating methods

    fun tryForceUpdate()
    fun performSoftUpdate()
}