package com.skosc.tkffintech.viewmodel

import com.skosc.tkffintech.entities.CourseInfo

data class CourseWithStatistics(
        val info: CourseInfo,
        val statistics: CourseStatistics
)