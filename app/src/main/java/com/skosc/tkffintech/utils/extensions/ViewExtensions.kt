package com.skosc.tkffintech.utils.extensions

import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.skosc.tkffintech.misc.Ratio

/**
 * Adds multiple [View]s to a single [ViewGroup]
 */
fun ViewGroup.addViews(vararg views: View) = views.forEach(this::addView)

/**
 * Setter for [ProgressBar] based on passed [Ratio]
 */
var ProgressBar.ratio: Ratio
    get() = Ratio(progress.toDouble(), max.toDouble())
    set(value) {
        progress = value.actual.toInt()
        max = value.max.toInt()
    }