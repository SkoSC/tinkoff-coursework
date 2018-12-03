package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.misc.model.UpdateResult
import io.reactivex.Single

interface CourseRepo {
    /**
     * All known courses for current user
     */
    val courses: Single<List<CourseInfo>>

    /**
     * List of users for passed course url
     */
    fun usersWithCourse(course: String): Single<List<User>>

    /**
     * Checks for updates, respecting cache policy
     */
    fun checkForUpdates(): Single<UpdateResult>

    /**
     * Loads courses info from network
     */
    fun tryForceRefresh(): Single<UpdateResult>
}