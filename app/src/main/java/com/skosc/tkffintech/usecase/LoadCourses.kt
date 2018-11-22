package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.repo.CourseRepo
import com.skosc.tkffintech.model.repo.HomeworkRepo

class LoadCourses(private val courseRepo: CourseRepo, private val homeworkRepo: HomeworkRepo) {
    val allCourses = courseRepo.courses

    fun tryLoadCourses() = courseRepo.tryForceUpdate()

    fun checkForUpdates() {
        courseRepo.performSoftUpdate()
        homeworkRepo.performSoftUpdate()
    }
}