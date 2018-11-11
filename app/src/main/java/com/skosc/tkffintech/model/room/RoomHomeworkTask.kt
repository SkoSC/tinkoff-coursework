package com.skosc.tkffintech.model.room

import androidx.room.*
import com.skosc.tkffintech.entities.HomeworkStatus

@Entity(tableName = "homework_task",
        foreignKeys = [ForeignKey(
                entity = RoomHomework::class,
                parentColumns = ["homework_id"],
                childColumns = ["homework_id_fk"],
                onDelete = ForeignKey.CASCADE
        )])
data class RoomHomeworkTask(
        @PrimaryKey
        val id: Long,

        @ColumnInfo(name = "homework_id_fk")
        val homeworkId: Long,

        @ColumnInfo(name = "title")
        val title: String,

        @Embedded
        val contest: RoomHomeworkContest,

        @ColumnInfo(name = "status")
        val status: HomeworkStatus,

        @ColumnInfo(name = "mark")
        val mark: String
)