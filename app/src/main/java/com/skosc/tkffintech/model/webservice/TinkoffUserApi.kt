package com.skosc.tkffintech.model.webservice

import com.google.gson.annotations.SerializedName
import com.skosc.tkffintech.entities.UserCredentials
import com.skosc.tkffintech.entities.UserInfo
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

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
    fun getCurrentUserInfo(@Header("Cookie") cookies: String): Single<Response<UserInfoResponce>>
}