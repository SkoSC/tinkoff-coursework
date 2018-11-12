package com.skosc.tkffintech.model.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.entities.HomeworkStatus
import com.skosc.tkffintech.entities.User

@Entity(tableName = "grade")
data class RoomGrade(
        @PrimaryKey
        @ColumnInfo(name = "grade_id")
        val id: Long,

        @ColumnInfo(name = "mark")
        val mark: String,

        @ColumnInfo(name = "status")
        val status: HomeworkStatus,

        @ColumnInfo(name = "user_id_fk")
        val userId: Long,

        @ColumnInfo(name = "task_id_fk")
        val taskId: Long
) {
    companion object {
        fun from(grade: HomeworkGrade): RoomGrade = RoomGrade(
                id = grade.id,
                mark = grade.mark,
                status = grade.status,
                userId = grade.user.id,
                taskId = grade.taskId
        )
    }

    fun convert(user: User): HomeworkGrade = HomeworkGrade(
            id = id,
            status = status,
            mark = mark,
            user = user,
            taskId = taskId
    )
}