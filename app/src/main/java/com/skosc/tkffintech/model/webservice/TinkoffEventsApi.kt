package com.skosc.tkffintech.model.webservice

import com.skosc.tkffintech.model.webservice.model.EventBucketResp
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface TinkoffEventsApi {
    @GET("/api/calendar/list/event")
    fun getAllEvents(): Single<Response<EventBucketResp>>
}