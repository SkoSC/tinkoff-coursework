package com.skosc.tkffintech.model.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skosc.tkffintech.entities.CourseInfo
import org.joda.time.DateTime

@Entity(tableName = "course_info")
data class RoomCourseInfo(
        @ColumnInfo(name = "title")
        val title: String,

        @ColumnInfo(name = "is_teacher")
        val isTeacher: Boolean,

        @ColumnInfo(name = "status")
        val status: CourseInfo.Status,

        @ColumnInfo(name = "starts")
        val starts: DateTime,

        @PrimaryKey
        @ColumnInfo(name = "url")
        val url: String
) {
    companion object {
        fun from(info: CourseInfo): RoomCourseInfo = RoomCourseInfo(
                title = info.title,
                isTeacher = info.isTeacher,
                starts = info.starts,
                status = info.status,
                url = info.url
        )
    }

    fun convert(): CourseInfo = CourseInfo(
            title = title,
            isTeacher = isTeacher,
            status = status,
            starts = starts,
            url = url
    )
}