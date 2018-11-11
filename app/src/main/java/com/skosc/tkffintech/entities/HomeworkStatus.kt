package com.skosc.tkffintech.entities

enum class HomeworkStatus {
    NEW, ACCEPTED, TEST_RESULT, UNKNOWN;

    companion object {
        fun form(value: String): HomeworkStatus = when(value.trim().toLowerCase()) {
            "new" -> NEW
            "accepted" -> ACCEPTED
            "contest_review" -> TEST_RESULT
            else -> UNKNOWN
        }
    }
}