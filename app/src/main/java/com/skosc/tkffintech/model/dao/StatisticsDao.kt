package com.skosc.tkffintech.model.dao

import io.reactivex.Observable

interface StatisticsDao {
    val totalScore: Observable<Double>
    val totalTestsCompleted: Observable<Int>
    val totalCurses: Observable<Int>

    fun setTotalScore(value: Double)
    fun setTotalTestsCompleted(value: Int)
    fun setTotalCurses(value: Int)
}