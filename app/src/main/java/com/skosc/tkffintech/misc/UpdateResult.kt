package com.skosc.tkffintech.misc

/**
 * Result of soft data updating
 */
sealed class UpdateResult {
    /**
     * Data updated and saved successful
     */
    object Updated : UpdateResult()

    /**
     * No need to update data
     */
    object NotUpdated : UpdateResult()

    /**
     * Data not updated, some error occurred
     */
    object Error : UpdateResult()
}