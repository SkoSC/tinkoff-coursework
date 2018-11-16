package com.skosc.tkffintech.model.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkTask

@Entity(tableName = "homework")
data class RoomHomework(
        @PrimaryKey
        @ColumnInfo(name = "homework_id")
        val id: Long,

        @ColumnInfo(name = "title")
        val title: String,

        @ColumnInfo(name = "course")
        val course: String
) {
    companion object {
        fun from(homework: Homework): RoomHomework = RoomHomework(
                id = homework.id,
                title = homework.title,
                course = homework.course
        )
    }

    fun convert(tasks: List<HomeworkTask>): Homework = Homework(
            id = id,
            course = course,
            title = title,
            tasks = tasks
    )
}