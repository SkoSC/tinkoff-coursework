package com.skosc.tkffintech.entities

/**
 * Homework, composing tasks by lesson
 */
data class Homework(
        val id: Long,
        val course: String,
        val title: String,
        val tasks: List<HomeworkTask>
)