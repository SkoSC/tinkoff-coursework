package com.skosc.tkffintech.model.webservice.model

import com.skosc.tkffintech.entities.EventInfo

data class EventBucketResp(
        val active: List<EventInfo> = listOf(),
        val archive: List<EventInfo> = listOf()
)