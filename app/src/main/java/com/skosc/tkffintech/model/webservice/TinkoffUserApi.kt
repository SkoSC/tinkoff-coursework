package com.skosc.tkffintech.model.webservice

import com.google.gson.annotations.SerializedName
import com.skosc.tkffintech.entities.UserCredentials
import com.skosc.tkffintech.entities.UserInfo
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface TinkoffUserApi {
    data class UserInfoResponce(
            @SerializedName("user")
            val body: UserInfo,
            val message: String,
            val status: String
    )

    @POST("signin")
    fun signIn(@Body body: UserCredentials): Single<Response<UserInfo>>

    @POST("signout")
    fun signOut(): Single<Response<Any>>

    @GET("user")
    @Headers("Content-Type: application/json")
    fun getCurrentUserInfo(): Single<Response<UserInfoResponce>>

    @POST("register_user")
    @Headers("content-type: application/json", "Referer: https://fintech.tinkoff.ru/edit")
    fun update(@Body user: UserInfo): Single<Response<UserInfoResponce>>
}