package com.skosc.tkffintech.entities

import org.joda.time.DateTime

data class EventInfo(
        val background: String = "",
        val title: String = "",
        val date: DateTime = DateTime(0),
        val type: EventType = EventType()
)