package com.skosc.tkffintech.misc

import com.skosc.tkffintech.R

object EventTypeIconFinder {
    fun findIconByEventType(name: String): Int {
        return when (name) {
            "Курсы для школьников" -> R.drawable.ic_event_study
            "Финтех Школа" -> R.drawable.ic_event_fintech
            "Летние стажировки" -> R.drawable.ic_event_staging
            "Стажировка" -> R.drawable.ic_event_staging
            else -> R.drawable.ic_event_generic
        }
    }
}