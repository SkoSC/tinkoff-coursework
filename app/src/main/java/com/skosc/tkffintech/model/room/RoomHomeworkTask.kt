package com.skosc.tkffintech.model.room

import androidx.room.*
import com.skosc.tkffintech.entities.HomeworkStatus
import com.skosc.tkffintech.entities.HomeworkTask

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
        val contest: RoomHomeworkContest,

        @ColumnInfo(name = "status")
        val status: HomeworkStatus,

        @ColumnInfo(name = "mark")
        val mark: String
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
                        deadlineDate = task.deadlineDate,
                        shortName = task.shotName
                ),
                status = task.status,
                mark = task.mark
        )
    }

    fun convert(): HomeworkTask = HomeworkTask(
            id = id,
            title = contest.title,
            contestId = contest.id,
            taskType = contest.type,
            maxScore = contest.maxScore,
            deadlineDate = contest.deadlineDate,
            shotName = contest.shortName,
            status = status,
            mark = mark
    )
}