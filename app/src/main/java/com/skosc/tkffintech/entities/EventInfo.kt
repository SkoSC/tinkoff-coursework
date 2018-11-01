package com.skosc.tkffintech.entities

import org.joda.time.DateTime

data class EventInfo(
        val title: String = "",
        val dateBegin: DateTime = DateTime(0),
        val dateEnd: DateTime = DateTime(0),
        val place: String = "",
        val type: EventType = EventType(),
        val url: String = "",
        val urlIsExternal: Boolean = false,
        val description: String = ""
)