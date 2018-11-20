package com.skosc.tkffintech.misc

/**
 * Result of soft data updating
 */
sealed class DataUpdateResult {
    /**
     * Data updated and saved successful
     */
    object Updated : DataUpdateResult()

    /**
     * No need to update data
     */
    object NotUpdated : DataUpdateResult()

    /**
     * Data not updated, some error occurred
     */
    object Error : DataUpdateResult()
}