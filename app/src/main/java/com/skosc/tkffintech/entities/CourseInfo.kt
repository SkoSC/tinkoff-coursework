package com.skosc.tkffintech.entities

import org.joda.time.DateTime

/**
 * Information about single course
 */
data class CourseInfo(
        val title: String,
        val isTeacher: Boolean,
        val status: Status,
        val starts: DateTime,
        val url: String // Unique, might be used as a key or id
) {
    enum class Status {
        ONGOING, UNKNOWN;
    }
}