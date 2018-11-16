package com.skosc.tkffintech.entities

import com.google.gson.annotations.SerializedName
import com.skosc.tkffintech.utils.cantorPairing
import org.joda.time.DateTime

data class EventInfo(
        @SerializedName("title")
        val title: String = "",

        @SerializedName("date_start")
        val dateBegin: DateTime = DateTime(0),

        @SerializedName("date_end")
        val dateEnd: DateTime = DateTime(0),

        @SerializedName("place")
        val place: String = "",

        @SerializedName("event_type")
        val type: EventType? = EventType(),

        @SerializedName("url")
        val url: String = "",

        @SerializedName("url_external")
        val urlIsExternal: Boolean = false,

        @SerializedName("description")
        val description: String = ""
) {
    val hid: Long
        get() {
            val titleHash = title.hashCode()
            val placeHash = place.hashCode()
            val descriptionHash = description.length.hashCode()
            val firstPart = cantorPairing(
                    cantorPairing(titleHash, placeHash), descriptionHash
            )

            val beginHash = dateBegin.hashCode()
            val endHash = dateEnd.hashCode()
            val secondPart = cantorPairing(beginHash, endHash)

            return cantorPairing(firstPart, secondPart).toLong()
        }
}