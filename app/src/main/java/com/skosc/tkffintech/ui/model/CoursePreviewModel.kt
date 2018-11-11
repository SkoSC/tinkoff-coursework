package com.skosc.tkffintech.ui.model

import com.skosc.tkffintech.entities.CourseInfo
import org.joda.time.DateTime

data class CoursePreviewModel(
        val title: String = "",
        val date: DateTime = DateTime(0),
        val score: Int = 0,
        val status: CourseInfo.Status = CourseInfo.Status.UNKNOWN,
        val url: String = ""
)