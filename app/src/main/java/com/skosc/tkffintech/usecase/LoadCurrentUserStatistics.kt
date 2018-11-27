package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.UserStatistics
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.model.repo.HomeworkRepo
import io.reactivex.Single
import io.reactivex.functions.Function3

class LoadCurrentUserStatistics(private val currentUserRepo: CurrentUserRepo, private val homeworkRepo: HomeworkRepo) {
    private val userStatisticsZipper = Function3(::UserStatistics)

    fun bundled(): Single<UserStatistics> {
        val user = currentUserRepo.idBlocking!!

        val totalScore = homeworkRepo.totalScore(user)
        val coursesCount = homeworkRepo.coursesCount(user)
        val testsCount = homeworkRepo.totalTests(user)

        return Single.zip(totalScore, testsCount, coursesCount, userStatisticsZipper)
    }
}