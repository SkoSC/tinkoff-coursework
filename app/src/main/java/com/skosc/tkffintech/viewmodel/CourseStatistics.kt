package com.skosc.tkffintech.viewmodel

import com.skosc.tkffintech.misc.Ratio

data class CourseStatistics(
        val homeworkRatio: Ratio,
        val testRatio: Ratio,
        val scoreRatio: Ratio,
        val completionRatio: Ratio
)