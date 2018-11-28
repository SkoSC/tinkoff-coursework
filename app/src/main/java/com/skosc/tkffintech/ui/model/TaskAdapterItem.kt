package com.skosc.tkffintech.ui.model

import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.entities.HomeworkStatus
import com.skosc.tkffintech.entities.HomeworkTask
import com.skosc.tkffintech.entities.composite.HomeworkWithGrades
import com.skosc.tkffintech.misc.model.Ratio
import com.skosc.tkffintech.utils.formatting.DateTimeFormatter

sealed class TaskAdapterItem {
    class Header(
            val title: String = ""
    ) : TaskAdapterItem()

    class Entry(
            val title: String = "",
            val score: Ratio = Ratio(),
            val info: String = "",
            val status: HomeworkStatus
    ) : TaskAdapterItem()
}

fun List<HomeworkWithGrades>.toAdapterItems(): List<TaskAdapterItem> {
    return this.flatMap { listOf(TaskAdapterItem.Header(it.homework.title)) + it.grades.toAdapterItems() }
}

@JvmName("tasksToAdapterItems")
fun List<Pair<HomeworkTask, HomeworkGrade>>.toAdapterItems(): List<TaskAdapterItem> {
    return this.map { pair ->
        val (task, grade) = pair
        TaskAdapterItem.Entry(
                task.title,
                Ratio(
                        grade.mark.toDouble(),
                        task.maxScore.toDouble()
                ),
                DateTimeFormatter.DATE_FORMATTER_SHORT_EU.print(task.deadlineDate),
                grade.status
        )
    }
}