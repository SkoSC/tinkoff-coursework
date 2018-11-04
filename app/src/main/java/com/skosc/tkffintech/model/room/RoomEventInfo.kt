package com.skosc.tkffintech.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skosc.rtu.utils.cantorPairing
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.entities.EventType
import org.joda.time.DateTime

@Entity(tableName = "event_info")
class RoomEventInfo(
        @PrimaryKey
        var id: Long = 0,

        @ColumnInfo(name = "title")
        var title: String = "",

        @ColumnInfo(name = "date_start")
        var dateBegin: DateTime = DateTime(0),

        @ColumnInfo(name = "date_end")
        var dateEnd: DateTime = DateTime(0),

        @ColumnInfo(name = "place")
        var place: String = "",

        @ColumnInfo(name = "type_name")
        var typeName: String = "",

        @ColumnInfo(name = "type_color")
        var typeColor: String = "",

        @ColumnInfo(name = "url")
        var url: String = "",

        @ColumnInfo(name = "url_external")
        var urlIsExternal: Boolean = false,

        @ColumnInfo(name = "description")
        var description: String = "",

        @ColumnInfo(name = "is_active")
        var isActive: Boolean = false
) {
    companion object {
        fun from(event: EventInfo, isActive: Boolean): RoomEventInfo {
            return RoomEventInfo(
                    id = makeId(event),
                    title = event.title,
                    dateBegin = event.dateBegin,
                    dateEnd = event.dateEnd,
                    place = event.place,
                    typeName = event.type?.name ?: "",
                    typeColor = event.type?.color ?: "",
                    url = event.url,
                    urlIsExternal = event.urlIsExternal,
                    description = event.description,
                    isActive = isActive
            )
        }

        private fun makeId(event: EventInfo): Long {
            val titleHash = event.title.hashCode()
            val placeHash = event.place.hashCode()
            val descriptionHash = event.description.length.hashCode()
            val firstPart = cantorPairing(
                    cantorPairing(titleHash, placeHash), descriptionHash
            ).toLong()

            val beginHash = event.dateBegin.hashCode()
            val endHash = event.dateEnd.hashCode()
            val secondPart = cantorPairing(beginHash, endHash)

            return firstPart
        }
    }

    fun convert(): EventInfo {
        return EventInfo(
                title = title,
                dateBegin = dateBegin,
                dateEnd = dateEnd,
                place = place,
                type = EventType(typeName, typeColor),
                url = url,
                urlIsExternal = urlIsExternal,
                description = description
        )
    }
}