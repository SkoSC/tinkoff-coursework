package com.skosc.tkffintech.misc

import java.lang.IllegalArgumentException

data class Ratio(val actual: Int, val max: Int) {
    init {
        if (actual > max) {
            throw IllegalArgumentException("Actual parameter can't be greater than max value")
        }
    }

    val left: Int = max - actual
}