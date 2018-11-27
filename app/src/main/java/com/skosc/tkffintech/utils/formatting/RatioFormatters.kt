package com.skosc.tkffintech.utils.formatting

import com.skosc.tkffintech.misc.Ratio

/**
 * Formats [Ratio] to string as percentage.
 * Example: Ratio(1, 2) = 50%
 */
fun Ratio.formatAsPersantage(): String {
    val hValue = (actual / max) * 100
    return "${Math.ceil(hValue)}%"
}

/**
 * Formats [Ratio] to string as / ratio.
 * Example: Ratio(1, 2) = 1/2
 */
fun Ratio.formatAsRatio(sep: String = "/"): String {
    return "$actual$sep$max"
}

/**
 * Formats [Ratio] to string as / ratio. Using as [NumberFormatter.userScore] as formatter
 * Example: Ratio(1, 2) = 1/2
 */
fun Ratio.formatAsScoreRatio(sep: String = "/"): String {
    val actual = NumberFormatter.userScore(this.actual)
    val max = NumberFormatter.userScore(this.max)

    return "$actual$sep$max"
}
