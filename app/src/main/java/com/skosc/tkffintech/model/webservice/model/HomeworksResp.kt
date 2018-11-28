package com.skosc.tkffintech.model.webservice.model

import com.google.gson.annotations.SerializedName
import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkTask
import com.skosc.tkffintech.entities.HomeworkTaskType
import org.joda.time.DateTime

data class HomeworksResp(val homeworks: List<HomeworkResp>) {
    data class HomeworkResp(val id: Long, val title: String, val tasks: List<TaskResp>) {
        fun convert(course: String): Homework = Homework(
                id = id,
                title = title,
                course = course,
                tasks = tasks.map { it.convert() }
        )
    }

    data class TaskResp(
            val id: Long,
            val status: String,
            val mark: String,
            @SerializedName("task") val info: TaskInfoResp
    ) {
        fun convert(): HomeworkTask = HomeworkTask(
                id = id,
                contestId = info.id,
                title = info.title,
                maxScore = info.maxScore,
                taskType = HomeworkTaskType.from(info.taskType),
                deadlineDate = info.deadline,
                shotName = info.shortName

        )
    }


    data class TaskInfoResp(
            val id: Long,
            val title: String,
            @SerializedName("task_type") val taskType: String,
            @SerializedName("max_score") val maxScore: String,
            @SerializedName("deadline_date") val deadline: DateTime,
            @SerializedName("short_name") val shortName: String

    )
}