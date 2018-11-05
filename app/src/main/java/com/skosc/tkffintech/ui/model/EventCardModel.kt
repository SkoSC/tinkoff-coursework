package com.skosc.tkffintech.ui.model

import android.content.Context
import com.skosc.tkffintech.entities.EventInfo
import org.joda.time.DateTime
import com.skosc.tkffintech.R
import java.io.Serializable

data class EventCardModel(val hid: Long, val title: String, val date: String, val typeTitle: String, val description: String) : Serializable {
    companion object {
        fun from(ctx: Context, eventInfo: EventInfo) : EventCardModel {
            return EventCardModel(
                    hid = eventInfo.hid,
                    title = eventInfo.title,
                    date = eventInfo.dateBegin.toString("YYYY-MM"),
                    typeTitle = eventInfo.type?.name ?: ctx.getString(R.string.event_type_unknown),
                    description = eventInfo.description
            )
        }
    }
}