package com.skosc.tkffintech.utils

import java.math.BigDecimal
import java.math.RoundingMode

object NumberFormatter {
    fun userScore(value: Double): String {
        return value.round(2).toString()
    }

    private fun Double.round(places: Int): Double {
        if (places < 0) {
            throw IllegalArgumentException("Rounding should be for more then 0 places")
        }
        val bd = BigDecimal(this)
        return bd.setScale(places, RoundingMode.HALF_UP)
                .toDouble()
    }
}