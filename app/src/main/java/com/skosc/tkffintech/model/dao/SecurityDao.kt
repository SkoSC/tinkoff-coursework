package com.skosc.tkffintech.model.dao

import io.reactivex.Single

interface SecurityDao {
    /**
     * Deletes login credentials of current user
     */
    fun clearAuthCredentials()

    /**
     * Checks if there is credentials for accessing api
     */
    val hasAuthCredentials: Single<Boolean>
}