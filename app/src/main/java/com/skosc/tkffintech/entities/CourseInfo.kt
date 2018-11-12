package com.skosc.tkffintech.entities

import org.joda.time.DateTime

data class CourseInfo(
        val title: String,
        val isTeacher: Boolean,
        val status: Status,
        val starts: DateTime,
        val url: String,
        val homeWorks: List<Homework>?
) {
    enum class Status {
        ONGOING, UNKNOWN;
    }
}