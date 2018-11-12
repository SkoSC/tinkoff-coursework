package com.skosc.tkffintech.ui.model

import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.misc.Ratio
import org.joda.time.DateTime

data class CourseDetailModel(
        val title: String = "",
        val date: DateTime = DateTime(0),
        val score: Ratio = Ratio(),
        val status: CourseInfo.Status = CourseInfo.Status.UNKNOWN,
        val url: String = "",
        val globalRate: Ratio = Ratio(),
        val testsPassed: Ratio = Ratio(),
        val homeworkCompleted: Ratio = Ratio(),
        val lessons: Ratio = Ratio()
)