package com.skosc.tkffintech.entities

data class HomeworkGrade(
        val id: Long,
        val mark: String,
        val status: HomeworkStatus,
        val user: User,
        val taskId: Long
)