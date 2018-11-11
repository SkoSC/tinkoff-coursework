package com.skosc.tkffintech.model.room

import androidx.room.Embedded
import androidx.room.Relation

data class RoomHomeworkAndTask(
        @Embedded
        val homework: RoomHomework,

        @Relation(parentColumn = "homework_id", entityColumn = "homework_id_fk")
        val tasks: List<RoomHomeworkTask>
)