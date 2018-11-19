package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkStatus
import com.skosc.tkffintech.entities.HomeworkTaskType
import com.skosc.tkffintech.misc.Ratio
import com.skosc.tkffintech.model.repo.HomeworkRepo
import com.skosc.tkffintech.viewmodel.CourseStatistics
import io.reactivex.Observable
import io.reactivex.Single

class CourseStatisticsCalculator(
        private val loadGrades: LoadGrades,
        private val homeworkRepo: HomeworkRepo
) {

    fun totalScore(course: String): Observable<Double> {
        return loadGrades.loadGradesForCurrentUser(course)
                .map { it.map { it.second }.flatMap { it.map { it.grade.mark } }.map { it.toDouble() } }
                .map { it -> it.reduce { a, b -> a + b } }
    }


    fun totalTests(course: String): Observable<Int> {
        return loadGrades.loadGradesForCurrentUser(course)
                .map { it.map { it.second }.flatMap { it.map { it.task.taskType } } }
                .map { it.filter { it == HomeworkTaskType.TEST } }
                .map { it.count() }
    }

    fun totalHomeworks(course: String): Observable<Int> {
        return homeworkRepo.homeworks(course).map { it.size }
    }

    fun bundled(course: String): Single<CourseStatistics> {
        return loadGrades.loadGradesForCurrentUser(course).doOnNext { it }
                .firstOrError()
                .map(this::calculateStatics)
    }

    private fun calculateStatics(it: List<Pair<Homework, List<LoadGrades.TaskWithGrade>>>): CourseStatistics {
        val homeworksTotal = it.size
        val homeworkCompleted = it
                .map { it.second }
                .filter { it.map { it.grade }.all {
                    // TODO Change filtering criteria
                    it.status == HomeworkStatus.ACCEPTED }
                }
                .count()

        val tests = it
                .flatMap { it.second }
                .filter { it.task.taskType == HomeworkTaskType.TEST }
        val testsTotal = tests.size
        val testsCompleted = tests
                .map { it.grade }
                .filter { it.status == HomeworkStatus.ACCEPTED }
                .size

        val scorePossible = tests.map { it.task.maxScore.toDouble() }.sum()
        val scoreTotal = tests.map { it.grade.mark.toDouble() }.sum()

        val homeworkRatio = Ratio(homeworkCompleted, homeworksTotal)
        val testRatio = Ratio(testsCompleted, testsTotal)
        val scoreRatio = Ratio(scoreTotal, scorePossible)
        val completionRatio = homeworkRatio + testRatio + scoreRatio
        return CourseStatistics(
                homeworkRatio = homeworkRatio,
                testRatio = testRatio,
                scoreRatio = scoreRatio,
                completionRatio = completionRatio
        )
    }
}