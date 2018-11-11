package com.skosc.tkffintech.model.room

import androidx.room.ColumnInfo
import com.skosc.tkffintech.entities.HomeworkTaskType
import org.joda.time.DateTime

data class RoomHomeworkContest(
        @ColumnInfo(name = "contest_id")
        val id: Long,

        @ColumnInfo(name = "contest_title")
        val title: String,

        @ColumnInfo(name = "contest_type")
        val type: HomeworkTaskType,

        @ColumnInfo(name = "max_score")
        val maxScore: String,

        @ColumnInfo(name = "deadline_date")
        val deadlineDate: DateTime,

        @ColumnInfo(name = "short_name")
        val shortName: String
)