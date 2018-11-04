@file:Suppress("NOTHING_TO_INLINE")

package com.skosc.tkffintech.utils

import android.view.View
import android.util.TypedValue


inline fun View.dp(value: Int): Float {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), metrics)
}

inline fun View.dp(value: Float): Float = dp(value.toInt())

inline fun View.sp(value: Int): Float {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value.toFloat(), metrics)
}