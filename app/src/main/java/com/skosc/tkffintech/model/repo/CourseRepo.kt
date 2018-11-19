package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.entities.User
import io.reactivex.Observable
import io.reactivex.Single

interface CourseRepo {
    val courses: Observable<List<CourseInfo>>
    fun tryForceUpdate(): Single<Boolean>
    fun usersWithCourse(course: String): Observable<List<User>>
    fun performSoftUpdate()
}