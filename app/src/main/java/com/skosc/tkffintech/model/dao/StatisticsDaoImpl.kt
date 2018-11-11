package com.skosc.tkffintech.model.dao

import android.content.SharedPreferences
import com.skosc.rxprefs.RxPreferences
import io.reactivex.Observable

class StatisticsDaoImpl(sp: SharedPreferences) : StatisticsDao {
    private val rxPreferences = RxPreferences(sp)

    private val totalScorePref = rxPreferences
            .getFloat("total-score", 0F)
    private val totalTestsCompletedPref = rxPreferences
            .getInteger("total-test-completed", 0)
    private val totalCursesPref = rxPreferences
            .getInteger("total-curses", 0)

    override val totalScore: Observable<Double> = totalScorePref
            .observable()
            .map(Float::toDouble)

    override val totalTestsCompleted: Observable<Int> = totalTestsCompletedPref
            .observable()


    override val totalCurses: Observable<Int> = totalCursesPref
            .observable()

    override fun setTotalScore(value: Double) {
        totalScorePref.post(value.toFloat())
    }

    override fun setTotalTestsCompleted(value: Int) {
        totalTestsCompletedPref.post(value)
    }

    override fun setTotalCurses(value: Int) {
        totalCursesPref.post(value)
    }
}