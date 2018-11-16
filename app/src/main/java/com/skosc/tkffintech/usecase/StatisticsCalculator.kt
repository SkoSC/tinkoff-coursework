package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.model.repo.HomeworkRepo
import io.reactivex.Observable

class StatisticsCalculator(private val homeworkRepo: HomeworkRepo, private val currentUserRepo: CurrentUserRepo) {
    val totalScore: Observable<Double> by lazy {
        currentUserRepo.id.flatMap { homeworkRepo.gradesSumForUser(it) }
    }
}