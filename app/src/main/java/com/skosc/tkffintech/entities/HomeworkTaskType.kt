package com.skosc.tkffintech.entities

enum class HomeworkTaskType {
    FULL, TEST, UNKNOWN;

    companion object {
        fun from(value: String): HomeworkTaskType = when (value.trim().toLowerCase()) {
            "full" -> FULL
            "test_during_lecture" -> TEST
            else -> UNKNOWN
        }
    }
}