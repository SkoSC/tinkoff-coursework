package com.skosc.tkffintech.entities

enum class HomeworkStatus {
    NEW, ACCEPTED, UNKNOWN;

    companion object {
        fun form(value: String): HomeworkStatus = when(value.trim().toLowerCase()) {
            "new" -> NEW
            "accepted" -> ACCEPTED
            else -> UNKNOWN
        }
    }
}