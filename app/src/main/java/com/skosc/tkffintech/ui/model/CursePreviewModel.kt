package com.skosc.tkffintech.ui.model

import com.skosc.tkffintech.entities.CurseStatus
import org.joda.time.DateTime

data class CursePreviewModel(
        val title: String,
        val date: DateTime,
        val score: Int,
        val status: CurseStatus
)