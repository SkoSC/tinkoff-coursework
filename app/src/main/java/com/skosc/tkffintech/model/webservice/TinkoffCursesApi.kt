package com.skosc.tkffintech.model.webservice

import com.google.gson.annotations.SerializedName
import com.skosc.tkffintech.entities.*
import io.reactivex.Single
import org.joda.time.DateTime
import retrofit2.http.GET
import retrofit2.http.Path

interface TinkoffCursesApi {
    data class ConnectionsResp(val courses: List<Course>) {
        data class Course(
                val title: String,
                @SerializedName("is_teacher")
                val isTeacher: Boolean,
                val status: String,
                @SerializedName("event_date_start")
                val starts: DateTime,
                val url: String
        ) {
            fun convert(): CourseInfo = CourseInfo(
                    title = title,
                    isTeacher = isTeacher,
                    status = parseStatus(status),
                    starts = starts,
                    url = url
            )

            private fun parseStatus(string: String) : CourseInfo.Status {
                val clean = string.trim().toLowerCase()
                return when(clean) {
                    "ongoing" -> CourseInfo.Status.ONGOING
                    else -> CourseInfo.Status.UNKNOWN
                }
            }
        }
    }

    data class HomeworksResp(val homeworks: List<HomeworkResp>) {
        data class HomeworkResp(val id: Long, val title: String, val tasks: List<TaskResp>) {
            fun convert(course: String) : Homework = Homework(
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

    @GET("connections")
    fun connections(): Single<ConnectionsResp>

    @GET("course/{course}/homeworks")
    fun homeworks(@Path("course") course: String): Single<HomeworksResp>
}