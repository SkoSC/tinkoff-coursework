package com.skosc.tkffintech.entities

/**
 * Status of homework, generally meaning completion stage
 */
enum class HomeworkStatus {
    NEW, ACCEPTED, TEST_RESULT, UNKNOWN;

    companion object {
        /**
         * Converts string to [HomeworkStatus]
         */
        fun form(value: String): HomeworkStatus = when (value.trim().toLowerCase()) {
            "new" -> NEW
            "accepted" -> ACCEPTED
            "contest_review" -> TEST_RESULT
            else -> UNKNOWN
        }
    }
}