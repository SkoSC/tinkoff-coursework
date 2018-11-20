package com.skosc.tkffintech.utils

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Provides number of formatting functions for numbers :)
 * All included functions build for satisfying UI needs in mind so, output may change often
 */
object NumberFormatter {
    fun userScore(value: Double): String {
        return value.round(2).toString()
    }

    /**
     * Rounds number "half up". Can be slow, should use faster implementation on big arrays of numbers
     */
    private fun Double.round(places: Int): Double {
        if (places < 0) {
            throw IllegalArgumentException("Rounding should be for more then 0 places")
        }
        val bd = BigDecimal(this)
        return bd.setScale(places, RoundingMode.HALF_UP)
                .toDouble()
    }
}