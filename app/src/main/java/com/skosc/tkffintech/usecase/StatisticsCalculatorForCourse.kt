package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.HomeworkTaskType
import com.skosc.tkffintech.model.repo.HomeworkRepo
import io.reactivex.Observable

class StatisticsCalculatorForCourse(
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
                .map { it.map { it.second }.flatMap {it.map { it.task.taskType }} }
                .map { it.filter { it == HomeworkTaskType.TEST } }
                .map { it.count() }
    }

    fun totalHomeworks(course: String): Observable<Int> {
        return homeworkRepo.homeworks(course).map { it.size }
    }

}