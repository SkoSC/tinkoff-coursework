package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.misc.UpdateResult
import io.reactivex.Single

interface CourseRepo {
    val courses: Single<List<CourseInfo>>
    fun usersWithCourse(course: String): Single<List<User>>

    fun checkForUpdates(): Single<UpdateResult>
    fun tryForceRefresh(): Single<UpdateResult>
}