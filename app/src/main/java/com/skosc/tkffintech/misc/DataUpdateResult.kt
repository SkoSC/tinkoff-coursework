package com.skosc.tkffintech.misc

sealed class DataUpdateResult {
    object Updated : DataUpdateResult()
    object NotUpdated : DataUpdateResult()
    object Error : DataUpdateResult()
}