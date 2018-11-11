package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkStatus
import com.skosc.tkffintech.entities.HomeworkTaskType
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

object StatisticsCalculator {
    fun calculateBasicStatistics(data: List<Homework>): Single<BasicStatistics> {
        return Single.fromCallable {
            val courses = data.distinctBy { it.course }.size
            val score = data.flatMap { it.tasks }.map { it.mark.toDouble() }.sum()
            val tests = data.flatMap { it.tasks }
                    .filter { it.taskType == HomeworkTaskType.TEST }
                    .filter { it.status == HomeworkStatus.ACCEPTED }
                    .size
            BasicStatistics(score = score, curses = courses, tests = tests)
        }.subscribeOn(Schedulers.computation())
    }
}