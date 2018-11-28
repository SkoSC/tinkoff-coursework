package com.skosc.tkffintech.model.webservice.model

import com.google.gson.annotations.SerializedName
import com.skosc.tkffintech.entities.UserInfo

data class UserInfoResp(
        @SerializedName("user")
        val body: UserInfo,
        val message: String,
        val status: String
)