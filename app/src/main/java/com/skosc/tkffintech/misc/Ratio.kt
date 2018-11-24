package com.skosc.tkffintech.misc

import kotlin.math.sqrt

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

    /**
     * Difference between [max] and [actual] values
     */
    val left: Double = max - actual

    /**
     * Ratio value in range from 0 to 1
     * */
    fun asDouble(): Double = actual / max

    infix fun mean(other: Ratio): Ratio {
        val thisRatio = this.asDouble()
        val otherRatio = other.asDouble()

        val combRatio = sqrt(thisRatio * otherRatio)

        return Ratio(combRatio, 1.0)
    }
}