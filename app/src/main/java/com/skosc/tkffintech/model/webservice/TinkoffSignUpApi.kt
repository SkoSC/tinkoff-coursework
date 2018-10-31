package com.skosc.tkffintech.model.webservice

import com.skosc.tkffintech.entities.UserCredentials
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

interface TinkoffSignUpApi {
    @POST("/signin")
    fun signIn(@Body body: UserCredentials): Single<String>

    @POST("/signout")
    fun signOut(): Single<Response<Any>>
}