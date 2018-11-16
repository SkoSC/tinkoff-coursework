package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.model.repo.HomeworkRepo
import io.reactivex.Observable

class StatisticsCalculator(private val homeworkRepo: HomeworkRepo, private val currentUserRepo: CurrentUserRepo) {
    val totalScore: Observable<Double> by lazy {
        currentUserRepo.id.flatMap { homeworkRepo.gradesSumForUser(it) }
    }

    val totalTests: Observable<Int> by lazy {
        currentUserRepo.id
                .flatMap { homeworkRepo.testGradesForUser(it) }
                .map { it.filter { it.mark.toDouble() > 0 } }
                .map { it.size }
    }

    val totalCourses: Observable<Int> by lazy {
        homeworkRepo.countAllCourses()
    }
}