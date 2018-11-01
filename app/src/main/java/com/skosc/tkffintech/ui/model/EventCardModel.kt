package com.skosc.tkffintech.ui.model

import android.content.Context
import com.skosc.tkffintech.entities.EventInfo

data class EventCardModel(val title: String) {
    companion object {
        fun from(ctx: Context, eventInfo: EventInfo) : EventCardModel {
            return EventCardModel(title = eventInfo.title)
        }
    }
}