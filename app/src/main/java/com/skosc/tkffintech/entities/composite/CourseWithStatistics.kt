package com.skosc.tkffintech.entities.composite

import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.entities.CourseStatistics

data class CourseWithStatistics(
        val info: CourseInfo,
        val statistics: CourseStatistics
)