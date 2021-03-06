package com.skosc.tkffintech.utils.extensions

import com.skosc.tkffintech.misc.model.UpdateResult
import retrofit2.Response

fun Response<*>.extractUpdateResult(): UpdateResult {
    return if (isSuccessful) {
        UpdateResult.Updated
    } else {
        UpdateResult.Error
    }
}