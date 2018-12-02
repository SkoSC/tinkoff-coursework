package com.skosc.tkffintech.model.webservice.model

import com.google.gson.annotations.SerializedName
import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.entities.HomeworkStatus
import com.skosc.tkffintech.entities.User

data class GradesResp(
        @SerializedName("id")
        val id: Long?,

        @SerializedName("grouped_tasks")
        val groupedTasks: List<List<GroupedTask>>,

        @SerializedName("name")
        val title: String,

        @SerializedName("grades")
        val students: List<Student>
) {
    data class GroupedTask(
            @SerializedName("id")
            val id: Long,

            @SerializedName("short_name")
            val shortName: String,

            @SerializedName("title")
            val title: String,

            @SerializedName("max_score")
            val maxScore: Double
    )

    data class Student(
            @SerializedName("student")
            val name: String,

            @SerializedName("student_id")
            val id: Long,

            @SerializedName("grades")
            val grades: List<Grade>
    ) {
        fun toUser(): User = User(id, name)
    }

    data class Grade(
            @SerializedName("id")
            val id: Long,

            @SerializedName("mark")
            val mark: String,

            @SerializedName("status")
            val status: String?
    ) {
        fun toBuissnesGrade(user: User, taskId: Long): HomeworkGrade = HomeworkGrade(
                id = id,
                mark = mark,
                status = HomeworkStatus.form(status ?: ""),
                user = user,
                taskId = taskId
        )
    }

    fun grades(): List<HomeworkGrade> {
        val tasks = this.groupedTasks.flatten()

        return this.students.map { student ->
            val studentId = student.id
            student.grades.mapIndexed { index, grade ->
                grade.toBuissnesGrade(student.toUser(), tasks[index].id)
            }
        }.flatten()
    }
}