package com.skosc.tkffintech.entities

data class Homework(
        val id: Long,
        val title: String,
        val tasks: List<HomeworkTask>
)