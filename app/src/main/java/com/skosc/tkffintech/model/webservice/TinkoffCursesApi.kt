package com.skosc.tkffintech.model.webservice

import com.skosc.tkffintech.model.webservice.model.ConnectionsResp
import com.skosc.tkffintech.model.webservice.model.HomeworksResp
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TinkoffCursesApi {
    @GET("connections")
    fun connections(): Single<Response<ConnectionsResp>>

    @GET("course/{course}/homeworks")
    fun homeworks(@Path("course") course: String): Single<HomeworksResp>
}