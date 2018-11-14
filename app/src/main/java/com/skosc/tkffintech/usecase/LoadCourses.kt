package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.repo.CourseRepo

class LoadCourses(repo: CourseRepo) {
    val allCourses = repo.courses
}