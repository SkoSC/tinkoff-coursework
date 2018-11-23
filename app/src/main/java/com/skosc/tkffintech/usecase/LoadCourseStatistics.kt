package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.repo.HomeworkRepo
import com.skosc.tkffintech.viewmodel.UserWithGradesSum
import io.reactivex.Single

class LoadCourseStatistics(private val homeworkRepo: HomeworkRepo) {
    fun gradeSumForUserOnCourse(course: String): Single<List<UserWithGradesSum>> {
        return homeworkRepo.gradesSum(course)
    }
}