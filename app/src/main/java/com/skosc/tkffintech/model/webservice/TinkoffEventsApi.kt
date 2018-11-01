package com.skosc.tkffintech.model.webservice

import com.skosc.tkffintech.entities.EventInfo
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface TinkoffEventsApi {
    data class EventBucket(
            val active: List<EventInfo> = listOf(),
            val archive: List<EventInfo> = listOf()
    )

    @GET("/api/calendar/list/event")
    fun getAllEvents(): Single<Response<EventBucket>>
}