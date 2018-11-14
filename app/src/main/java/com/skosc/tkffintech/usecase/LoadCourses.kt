package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.repo.CourseRepo

class LoadCourses(private val repo: CourseRepo) {
    val allCourses = repo.courses

    fun tryLoadCourses() = repo.tryToUpdateAll()
}