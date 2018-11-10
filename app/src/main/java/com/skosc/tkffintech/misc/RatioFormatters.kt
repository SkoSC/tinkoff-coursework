package com.skosc.tkffintech.misc

fun Ratio.formatAsPersantage(): String {
    val hValue = (actual.toDouble() / max.toDouble()) * 100
    return "${Math.ceil(hValue)}%"
}

fun Ratio.formatAsRatio(sep: String): String {
    return "$actual$sep$max"
}

