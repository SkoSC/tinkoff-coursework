package com.skosc.tkffintech.model.room.adapter

import androidx.room.TypeConverter
import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.entities.HomeworkStatus
import org.joda.time.DateTime


class HomeworkCourseStatusAdapter {
    @TypeConverter
    fun fromCourseStatus(value: Int): CourseInfo.Status {
        return when(value) {
            1 -> CourseInfo.Status.ONGOING
            else -> CourseInfo.Status.UNKNOWN
        }
    }

    @TypeConverter
    fun courseStatusToInt(date: CourseInfo.Status): Int {
        return when(date) {
            CourseInfo.Status.ONGOING -> 1
            else -> 0
        }
    }
}