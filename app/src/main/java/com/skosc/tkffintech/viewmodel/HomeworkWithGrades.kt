package com.skosc.tkffintech.viewmodel

import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.entities.HomeworkTask

data class HomeworkWithGrades(val homework: Homework, val grades: List<Pair<HomeworkTask, HomeworkGrade>>)