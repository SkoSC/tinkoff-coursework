package com.skosc.tkffintech.ui.model

import android.content.Context
import com.skosc.tkffintech.entities.EventInfo
import org.joda.time.DateTime
import com.skosc.tkffintech.R

data class EventCardModel(val title: String, val date: String, val typeTitle: String) {
    companion object {
        fun from(ctx: Context, eventInfo: EventInfo) : EventCardModel {
            return EventCardModel(
                    title = eventInfo.title,
                    date = eventInfo.dateBegin.toString("YYYY-MM"),
                    typeTitle = eventInfo.type?.name ?: ctx.getString(R.string.event_type_unknown)
            )
        }
    }
}