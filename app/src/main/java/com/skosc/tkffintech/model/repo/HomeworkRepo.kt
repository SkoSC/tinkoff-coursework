package com.skosc.tkffintech.model.repo

import io.reactivex.Observable


interface HomeworkRepo {
    val statisticsScore: Observable<Double>
    val statisticsTestsCompleted: Observable<Int>
    val statisticsCurses: Observable<Int>

    fun update()
}