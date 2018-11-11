package com.skosc.tkffintech.ui.model

import com.skosc.tkffintech.misc.Ratio

sealed class TaskAdapterItem {
    class Header(
            val title: String = ""
    ) : TaskAdapterItem()

    class Entry(
            val title: String = "",
            val score: Ratio = Ratio(0, 0),
            val info: String = ""
    ) : TaskAdapterItem()
}