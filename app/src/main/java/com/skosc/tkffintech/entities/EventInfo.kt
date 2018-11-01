package com.skosc.tkffintech.entities

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class EventInfo(
        @SerializedName("title")
        val title: String = "",

        @SerializedName("date_start")
        val dateBegin: DateTime = DateTime(0),

        @SerializedName("date_end")
        val dateEnd: DateTime = DateTime(0),

        @SerializedName("place")
        val place: String = "",

        @SerializedName("event_type")
        val type: EventType? = EventType(),

        @SerializedName("url")
        val url: String = "",

        @SerializedName("url_external")
        val urlIsExternal: Boolean = false,

        @SerializedName("description")
        val description: String = ""
)