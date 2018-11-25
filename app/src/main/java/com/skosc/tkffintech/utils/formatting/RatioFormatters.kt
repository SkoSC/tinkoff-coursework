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

