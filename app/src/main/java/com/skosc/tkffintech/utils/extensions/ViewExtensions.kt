package com.skosc.tkffintech.utils.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.skosc.tkffintech.misc.model.Ratio

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

/**
 * Shortcut for crating [TextWatcher] only for [TextWatcher.afterTextChanged] method.
 * Not passes null to listener
 */
fun TextView.addAfterTextChangedListener(listener: (s: Editable) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                listener(s)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}