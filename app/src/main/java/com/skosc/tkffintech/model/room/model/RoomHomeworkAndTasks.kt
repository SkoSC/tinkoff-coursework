package com.skosc.tkffintech.model.room.model

import androidx.room.Embedded
import androidx.room.Relation
import com.skosc.tkffintech.entities.Homework

data class RoomHomeworkAndTasks(
        @Embedded
        val homework: RoomHomework,

        @Relation(parentColumn = "homework_id", entityColumn = "homework_id_fk")
        val tasks: List<RoomHomeworkTask>
) {
    companion object {
        fun from(homework: RoomHomeworkAndTasks): Homework = Homework(
                id = homework.homework.id,
                title = homework.homework.title,
                course = homework.homework.course,
                tasks = homework.tasks.map { it.convert() }
        )
    }

    fun convert(): Homework = Homework(
            id = homework.id,
            title = homework.title,
            course = homework.course,
            tasks = tasks.map { it.convert() }
    )
}