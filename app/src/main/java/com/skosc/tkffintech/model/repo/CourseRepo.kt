package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.misc.DataUpdateResult
import io.reactivex.Observable
import io.reactivex.Single

interface CourseRepo {
    val courses: Single<List<CourseInfo>>
    fun checkForUpdates(): Single<DataUpdateResult>
    fun tryForceRefresh(): Single<DataUpdateResult>
}