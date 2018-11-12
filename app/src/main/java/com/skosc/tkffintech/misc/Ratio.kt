package com.skosc.tkffintech.misc

data class Ratio(val actual: Double = 0.0, val max: Double = 0.0) {
    constructor(actual: Int, max: Int): this(actual.toDouble(), max.toDouble())

    init {
        if (actual > max) {
            throw IllegalArgumentException("Actual parameter can't be greater than max value")
        }
    }

    val left: Double = max - actual
}