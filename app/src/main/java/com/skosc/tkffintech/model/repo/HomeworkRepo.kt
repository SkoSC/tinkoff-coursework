package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.composite.HomeworkWithGrades
import com.skosc.tkffintech.entities.composite.UserWithGradesSum
import com.skosc.tkffintech.misc.UpdateResult
import io.reactivex.Single

interface HomeworkRepo {
    fun grades(user: Long, course: String): Single<List<HomeworkWithGrades>>
    fun gradesSum(course: String): Single<List<UserWithGradesSum>>

    fun checkForUpdates(course: String): Single<UpdateResult>
    fun tryForceRefresh(course: String): Single<UpdateResult>
}