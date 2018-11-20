package com.skosc.tkffintech.model.room.model

import androidx.room.Embedded

data class RoomHomworkToTasks(
        @Embedded
        val homework: RoomHomework,

        @Embedded
        val task: RoomHomeworkTask,

        @Embedded
        val grade: RoomGrade
)