package com.skosc.tkffintech.model.room.model

import androidx.room.*
import com.skosc.tkffintech.entities.HomeworkTask
import org.joda.time.DateTime

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

        @Embedded
        val contest: RoomHomeworkContest
) {
    companion object {
        fun from(task: HomeworkTask, parentId: Long): RoomHomeworkTask = RoomHomeworkTask(
                id = task.id,
                homeworkId = parentId,
                contest = RoomHomeworkContest(
                        id = task.contestId,
                        title = task.title,
                        type = task.taskType,
                        maxScore = task.maxScore,
                        deadlineDate = task.deadlineDate ?: DateTime(0),
                        shortName = task.shotName
                )
        )
    }

    fun convert(): HomeworkTask = HomeworkTask(
            id = id,
            title = contest.title,
            contestId = contest.id,
            taskType = contest.type,
            maxScore = contest.maxScore,
            deadlineDate = contest.deadlineDate,
            shotName = contest.shortName
    )
}