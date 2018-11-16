package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.entities.User
import io.reactivex.Observable

interface CourseRepo {
    val courses: Observable<List<CourseInfo>>
    fun tryToUpdateAll()

    fun usersWithCourse(course: String): Observable<List<User>>
}