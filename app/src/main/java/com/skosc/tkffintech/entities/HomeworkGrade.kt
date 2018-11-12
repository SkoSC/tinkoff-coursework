package com.skosc.tkffintech.entities

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class HomeworkGrade(
    val id: Long,
    val mark: String,
    val status: HomeworkStatus,
    val user: User,
    val taskId: Long
)