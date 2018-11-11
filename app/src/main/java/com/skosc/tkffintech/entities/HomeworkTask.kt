package com.skosc.tkffintech.entities

import org.joda.time.DateTime

data class HomeworkTask(
        val id: Long,
        val contestId: Long,
        val title: String,
        val taskType: HomeworkTaskType,
        val maxScore: String,
        val deadlineDate: DateTime,
        val shotName: String,
        val status: HomeworkStatus,
        val mark: String
)