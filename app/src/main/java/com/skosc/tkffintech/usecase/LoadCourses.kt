package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.repo.CourseRepo

class LoadCourses(private val repo: CourseRepo) {
    val courses = repo.courses
    fun checkForUpdates() = repo.checkForUpdates()
    fun forceRefresh() = repo.tryForceRefresh()
}