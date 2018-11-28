package com.skosc.tkffintech.model.webservice.model

import com.google.gson.annotations.SerializedName
import com.skosc.tkffintech.entities.CourseInfo
import org.joda.time.DateTime

data class ConnectionsResp(val courses: List<Course>) {
    data class Course(
            val title: String,
            @SerializedName("is_teacher")
            val isTeacher: Boolean,
            val status: String,
            @SerializedName("event_date_start")
            val starts: DateTime,
            val url: String
    ) {
        fun convert(): CourseInfo = CourseInfo(
                title = title,
                isTeacher = isTeacher,
                status = parseStatus(status),
                starts = starts,
                url = url
        )

        private fun parseStatus(string: String): CourseInfo.Status {
            val clean = string.trim().toLowerCase()
            return when (clean) {
                "ongoing" -> CourseInfo.Status.ONGOING
                else -> CourseInfo.Status.UNKNOWN
            }
        }
    }
}