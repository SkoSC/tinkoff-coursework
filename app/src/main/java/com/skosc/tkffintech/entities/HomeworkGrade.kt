package com.skosc.tkffintech.entities

/**
 * Single grade connected to particular [HomeworkTask]
 */
data class HomeworkGrade(
        val id: Long,
        val mark: String,
        val status: HomeworkStatus,
        val taskId: Long,
        val user: User
)