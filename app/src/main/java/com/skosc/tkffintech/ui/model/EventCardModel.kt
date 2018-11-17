package com.skosc.tkffintech.ui.model

import android.content.Context
import com.skosc.tkffintech.R
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.misc.ChipColors
import com.skosc.tkffintech.utils.DateTimeFormatter.DATE_FORMATTER_SHORT_EU
import java.io.Serializable

data class EventCardModel(
        val hid: Long,
        val title: String,
        val date: String,
        val typeTitle: String,
        val typeColor: Int,
        val description: String
) : Serializable {
    companion object {
        const val UNKNOWN_COLOR = "unknown"

        fun from(ctx: Context, eventInfo: EventInfo): EventCardModel {
            return EventCardModel(
                    hid = eventInfo.hid,
                    title = eventInfo.title,
                    date = DATE_FORMATTER_SHORT_EU.print(eventInfo.dateBegin),
                    typeTitle = eventInfo.type?.name ?: ctx.getString(R.string.event_type_unknown),
                    typeColor = ChipColors.colorForName(eventInfo.type?.color ?: UNKNOWN_COLOR),
                    description = eventInfo.description
            )
        }
    }
}

fun List<EventInfo>.toAdapterModels(context: Context): List<EventCardModel> {
    return this.map { EventCardModel.from(context, it) }
}