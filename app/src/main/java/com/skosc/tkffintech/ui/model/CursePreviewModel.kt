package com.skosc.tkffintech.ui.model

import com.skosc.tkffintech.entities.CurseStatus
import org.joda.time.DateTime

data class CursePreviewModel(
        val title: String = "",
        val date: DateTime = DateTime(0),
        val score: Int = 0,
        val status: CurseStatus = CurseStatus.UNKNOWN,
        val url: String = ""
)