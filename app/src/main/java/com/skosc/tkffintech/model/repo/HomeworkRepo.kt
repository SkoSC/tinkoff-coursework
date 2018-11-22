package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.entities.TaskWithGrade
import com.skosc.tkffintech.misc.DataUpdateResult
import com.skosc.tkffintech.viewmodel.UserWithGradesSum
import io.reactivex.Observable
import io.reactivex.Single


interface HomeworkRepo {
    fun checkForUpdates(course: String): Single<DataUpdateResult>
    fun tryForceRefresh(course: String): Single<DataUpdateResult>
}