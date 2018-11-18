package com.skosc.tkffintech.viewmodel

import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.misc.Ratio

data class CourseWithStatistics(
        val info: CourseInfo,
        val statistics: CourseStatistics
)