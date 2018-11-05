@file:Suppress("NOTHING_TO_INLINE")

package com.skosc.tkffintech.utils

import android.util.TypedValue
import android.view.View


inline fun View.dp(value: Int): Float {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), metrics)
}

inline fun View.dp(value: Float): Float = dp(value.toInt())

inline fun View.sp(value: Int): Float {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value.toFloat(), metrics)
}