package com.skosc.tkffintech.model.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
        tableName = "user_course_rel",
        indices = [Index(
                value = ["user_id", "course_url"],
                unique = true
        )
        ])
data class RoomUserCourseRelation(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,

        @ColumnInfo(name = "user_id")
        val userId: Long = 0,

        @ColumnInfo(name = "course_url")
        val courseUrl: String = ""
)