package com.skosc.tkffintech.ui.model

import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.misc.Ratio
import org.joda.time.DateTime

data class CurseDetailModel(
        val title: String = "",
        val date: DateTime = DateTime(0),
        val score: Ratio = Ratio(0, 0),
        val status: CourseInfo.Status = CourseInfo.Status.UNKNOWN,
        val url: String = "",
        val globalRate: Ratio = Ratio(0, 0),
        val testsPassed: Ratio = Ratio(0, 0),
        val homeworkCompleted: Ratio = Ratio(0, 0),
        val lessons: Ratio = Ratio(0, 0)
)