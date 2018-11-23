package com.skosc.tkffintech.utils

import com.skosc.tkffintech.misc.UpdateResult
import retrofit2.Response

fun Response<*>.extractUpdateResult(): UpdateResult {
    return if (isSuccessful) {
        UpdateResult.Updated
    } else {
        UpdateResult.Error
    }
}