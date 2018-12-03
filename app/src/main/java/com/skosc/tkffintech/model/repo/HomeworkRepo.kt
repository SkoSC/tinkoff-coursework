package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.composite.HomeworkWithGrades
import com.skosc.tkffintech.entities.composite.UserWithGradesSum
import com.skosc.tkffintech.misc.model.UpdateResult
import io.reactivex.Single

interface HomeworkRepo {
    /**
     * All grades for passed user, filtered by course
     */
    fun grades(user: Long, course: String): Single<List<HomeworkWithGrades>>

    /**
     * All grades for passed user for all courses
     */
    fun grades(user: Long): Single<List<HomeworkWithGrades>>

    /**
     * Grades sum for all users with course
     */
    fun gradesSum(course: String): Single<List<UserWithGradesSum>>

    /**
     * Number all all known courses
     */
    fun coursesCount(user: Long): Single<Int>

    /**
     * Total score of user for all courses and homeworks
     */
    fun totalScore(user: Long): Single<Double>

    /**
     * Total tests passed by user
     */
    fun totalTests(user: Long): Single<Int>


    /**
     * Loads courses and homeworks from network, with respect to cache policy and network status
     */
    fun checkForUpdates(course: String): Single<UpdateResult>

    /**
     * Loads courses and homeworks from network
     */
    fun tryForceRefresh(course: String): Single<UpdateResult>
}