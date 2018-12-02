package com.skosc.tkffintech.model.service

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.skosc.tkffintech.entities.GeoAddress
import io.reactivex.Single

class GeoSearcher(private val context: Context) {
    private val geocoder: Geocoder by lazy { Geocoder(context) }

    /**
     * Performs network search for object's [GeoAddress] with given name
     */
    fun findAll(names: List<String>): Single<List<GeoAddress>> {
        return Single.fromCallable {
            names.map { name -> findAddress(name) }
                    .filter { it != null }
                    .map { GeoAddress(title = it!!.featureName, lat = it.latitude, lon = it.longitude) }
        }
    }

    private fun findAddress(name: String): Address? {
        return try {
            geocoder.getFromLocationName(name, 1).firstOrNull()
        } catch (e: Exception) {
            null
        }
    }
}