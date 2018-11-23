package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.misc.UpdateResult
import io.reactivex.Single

interface CourseRepo {
    val courses: Single<List<CourseInfo>>
    fun checkForUpdates(): Single<UpdateResult>
    fun tryForceRefresh(): Single<UpdateResult>
}