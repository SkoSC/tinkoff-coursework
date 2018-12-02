@file:Suppress("NOTHING_TO_INLINE")

package com.skosc.tkffintech.utils.extensions

import android.util.TypedValue
import android.view.View

/**
 * Convert dp to pixels
 */
inline fun View.dp(value: Int): Float {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), metrics)
}

/**
 * Convert dp to pixels
 */
inline fun View.dp(value: Float): Float = dp(value.toInt())

