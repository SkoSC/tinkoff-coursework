package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import com.skosc.tkffintech.usecase.StatisticsCalculator
import io.reactivex.subjects.BehaviorSubject

class HomeworkRepoImpl(private val api: TinkoffCursesApi) : HomeworkRepo {
    override val statisticsScore: BehaviorSubject<Double> = BehaviorSubject.create()
    override val statisticsTestsCompleted: BehaviorSubject<Int> = BehaviorSubject.create()
    override val statisticsCurses: BehaviorSubject<Int> = BehaviorSubject.create()

    override fun update() {
        api.homeworks("android_fall2018").subscribe { resp ->
            val homework = resp.homeworks.map { it.convert("android_fall2018") }
            StatisticsCalculator
                    .calculateBasicStatistics(homework)
                    .subscribe { stats ->
                        statisticsScore.onNext(stats.score)
                        statisticsTestsCompleted.onNext(stats.tests)
                        statisticsCurses.onNext(stats.curses)
                    }
        }
    }
}