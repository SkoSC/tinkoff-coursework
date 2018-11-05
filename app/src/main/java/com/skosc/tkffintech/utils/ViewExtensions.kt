package com.skosc.tkffintech.utils

import android.view.View
import android.view.ViewGroup

fun ViewGroup.addViews(vararg views: View) = views.forEach(this::addView)
