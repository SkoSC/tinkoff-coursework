package com.skosc.tkffintech.misc

/**
 * Describes ratio between two values. Useful for describing loading bars, scores and other similar things
 */
data class Ratio(val actual: Double = 0.0, val max: Double = 0.0) {
    constructor(actual: Int, max: Int) : this(actual.toDouble(), max.toDouble())

    init {
        if (actual > max) {
            throw IllegalArgumentException("Actual parameter can't be greater than max value")
        }
    }

    val left: Double = max - actual

    operator fun plus(other: Ratio): Ratio {
        return Ratio(this.actual * other.actual, this.max * other.max)
    }
}