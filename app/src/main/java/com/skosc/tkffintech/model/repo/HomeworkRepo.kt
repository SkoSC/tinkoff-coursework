package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.misc.UpdateResult
import com.skosc.tkffintech.viewmodel.HomeworkWithGrades
import io.reactivex.Single

interface HomeworkRepo {
    fun grades(user: Long, course: String): Single<List<HomeworkWithGrades>>

    fun checkForUpdates(course: String): Single<UpdateResult>
    fun tryForceRefresh(course: String): Single<UpdateResult>
}