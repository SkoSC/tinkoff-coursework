package com.skosc.tkffintech.utils

import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.skosc.tkffintech.misc.Ratio

fun ViewGroup.addViews(vararg views: View) = views.forEach(this::addView)

var ProgressBar.ratio: Ratio
    get() = Ratio(progress.toDouble(), max.toDouble())
    set(value) {
        progress = value.actual.toInt()
        max = value.max.toInt()
    }