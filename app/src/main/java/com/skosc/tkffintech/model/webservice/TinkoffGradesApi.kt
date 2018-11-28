package com.skosc.tkffintech.model.webservice

import com.skosc.tkffintech.model.webservice.model.GradesResp
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TinkoffGradesApi {
    @GET("course/{course}/grades")
    fun gradesForCourse(@Path("course") course: String): Single<Response<List<GradesResp>>>
}