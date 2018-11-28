package com.skosc.tkffintech.entities

import com.skosc.tkffintech.misc.model.Ratio

data class CourseStatistics(
        val homeworkRatio: Ratio,
        val testRatio: Ratio,
        val scoreRatio: Ratio,
        val completionRatio: Ratio
)