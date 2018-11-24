package com.skosc.tkffintech.entities

import com.skosc.tkffintech.R

/**
 * Status of homework, generally meaning completion stage
 */
enum class HomeworkStatus(@Transient val resourse: Int) {
    NEW(R.string.homework_status_new),
    ACCEPTED(R.string.homework_status_accepted),
    TEST_RESULT(R.string.homework_status_test_result),
    UNKNOWN(R.string.homework_status_unknown);

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