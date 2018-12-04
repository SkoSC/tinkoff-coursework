package com.skosc.tkffintech.model.webservice

import com.skosc.tkffintech.entities.UserCredentials
import com.skosc.tkffintech.entities.UserInfo
import com.skosc.tkffintech.model.webservice.model.UserInfoResp
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface TinkoffUserApi {

    @POST("signin")
    fun signIn(@Body body: UserCredentials): Single<Response<UserInfo>>

    @GET("user")
    @Headers("Content-Type: application/json")
    fun getCurrentUserInfo(): Single<Response<UserInfoResp>>

    @PUT("register_user")
    @Headers("Referer: https://fintech.tinkoff.ru")
    fun update(@Body user: UserInfo): Single<Response<UserInfoResp>>
}