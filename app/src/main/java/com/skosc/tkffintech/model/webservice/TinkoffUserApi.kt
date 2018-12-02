package com.skosc.tkffintech.model.webservice

import com.skosc.tkffintech.entities.UserCredentials
import com.skosc.tkffintech.entities.UserInfo
import com.skosc.tkffintech.model.webservice.model.UserInfoResp
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface TinkoffUserApi {

    @POST("signin")
    fun signIn(@Body body: UserCredentials): Single<Response<UserInfo>>

    @GET("user")
    @Headers("Content-Type: application/json")
    fun getCurrentUserInfo(): Single<Response<UserInfoResp>>

    @POST("register_user")
    @Headers("content-type: application/json", "Referer: https://fintech.tinkoff.ru/edit")
    fun update(@Body user: UserInfo): Single<Response<UserInfoResp>>
}