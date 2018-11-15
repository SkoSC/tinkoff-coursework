package com.skosc.tkffintech.ui.model

import com.skosc.tkffintech.entities.HomeworkStatus
import com.skosc.tkffintech.misc.Ratio

sealed class TaskAdapterItem {
    class Header(
            val title: String = ""
    ) : TaskAdapterItem()

    class Entry(
            val title: String = "",
            val score: Ratio = Ratio(),
            val info: String = "",
            val status: HomeworkStatus
    ) : TaskAdapterItem()
}