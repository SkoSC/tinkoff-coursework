package com.skosc.tkffintech.entities

/**
 * Task of homework in tinkoff fintech
 */
enum class HomeworkTaskType {
    FULL, TEST, UNKNOWN;

    companion object {
        /**
         * Converts string to [HomeworkTaskType]
         */
        fun from(value: String): HomeworkTaskType = when (value.trim().toLowerCase()) {
            "full" -> FULL
            "test_during_lecture" -> TEST
            else -> UNKNOWN
        }
    }
}