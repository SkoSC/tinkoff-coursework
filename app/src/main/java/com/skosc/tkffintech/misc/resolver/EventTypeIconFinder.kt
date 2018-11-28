package com.skosc.tkffintech.misc.resolver

import com.skosc.tkffintech.R

/**
 * Helper methods for finding topIcon for event
 */
object EventTypeIconFinder {

    /**
     * Resolves topIcon by event type, if not found fallbacks to [R.drawable.ic_event_generic]
     */
    fun findIconByEventType(name: String): Int {
        return when (name.trim()) {
            "Курсы для школьников" -> R.drawable.ic_event_study
            "Финтех Школа" -> R.drawable.ic_event_fintech
            "Летние стажировки" -> R.drawable.ic_event_staging
            "Стажировка" -> R.drawable.ic_event_staging
            else -> R.drawable.ic_event_generic
        }
    }
}