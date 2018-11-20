package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.entities.HomeworkTask

data class TaskWithGrade(
        val task: HomeworkTask,
        val grade: HomeworkGrade)