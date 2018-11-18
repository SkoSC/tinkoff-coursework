package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkStatus
import com.skosc.tkffintech.entities.HomeworkTaskType
import com.skosc.tkffintech.misc.Ratio
import com.skosc.tkffintech.model.repo.HomeworkRepo
import com.skosc.tkffintech.viewmodel.CourseStatistics
import com.skosc.tkffintech.viewmodel.CourseWithStatistics
import io.reactivex.Observable
import io.reactivex.Single

class CourseStatisticsCalculatorForCurrentUser(
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
        return loadGrades.loadGradesForCurrentUser(course)
                .firstOrError()
                .map(this::calculateStatics)
    }

    private fun calculateStatics(it: List<Pair<Homework, List<LoadGrades.TaskWithGrade>>>): CourseStatistics {
        val homeworksTotal = it.size
        val homeworkCompleted = it
                .map { it.second }
                .filter { it.map { it.grade }.all { it.status == HomeworkStatus.ACCEPTED } }
                .count()

        return CourseStatistics(
                homeworkRatio = Ratio(homeworkCompleted, homeworksTotal)
        )
    }
}