package com.skosc.tkffintech.ui.model

import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.misc.Ratio
import org.joda.time.DateTime

data class CourseDetailModel(
        val title: String = "",
        val url: String = "",
        val scoreRate: Ratio = Ratio(),
        val testsPassed: Ratio = Ratio(),
        val homeworkCompleted: Ratio = Ratio(),
        val completionRatio: Ratio = Ratio()
)