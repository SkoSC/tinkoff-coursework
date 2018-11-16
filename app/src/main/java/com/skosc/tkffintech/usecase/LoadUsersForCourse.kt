package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.model.repo.CourseRepo
import io.reactivex.Observable

class LoadUsersForCourse(private val courseRepo: CourseRepo) {
    fun load(course: String): Observable<List<User>> = courseRepo
            .usersWithCourse(course)
}