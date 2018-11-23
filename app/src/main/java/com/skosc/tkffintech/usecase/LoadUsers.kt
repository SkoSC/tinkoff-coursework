package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.model.repo.CourseRepo
import io.reactivex.Single

class LoadUsers(private val courseRepo: CourseRepo) {
    fun loadUsersWithCourse(course: String): Single<List<User>> {
        return courseRepo.usersWithCourse(course)
    }
}