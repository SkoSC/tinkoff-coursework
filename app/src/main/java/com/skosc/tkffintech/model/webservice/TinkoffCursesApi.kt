package com.skosc.tkffintech.model.webservice

import com.google.gson.annotations.SerializedName
import com.skosc.tkffintech.entities.CourseInfo
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTime
import retrofit2.http.GET

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

    data class HomeworksResp(val homeworks: List<Homework>) {
        data class Homework(val id: Int, val title: String, val tasks: List<Task>)
        data class Task(
                val id: Int,
                val status: String,
                val mark: String,
                @SerializedName("task") val info: TaskInfo
        )
        data class TaskInfo(
                val id: Int,
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
    fun homeworks(course: String): Single<HomeworksResp>
}