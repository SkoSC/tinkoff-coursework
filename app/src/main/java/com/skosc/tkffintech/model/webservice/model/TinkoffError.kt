package com.skosc.tkffintech.model.webservice.model

import com.google.gson.annotations.SerializedName

data class TinkoffError(
        @SerializedName("detail")
        val detail: String
)