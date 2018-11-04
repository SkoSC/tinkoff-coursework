package com.skosc.tkffintech.model.room.adapter

import androidx.room.TypeConverter
import org.joda.time.DateTime


class DateTimeAdapter {
    @TypeConverter
    fun fromTimestamp(value: Long): DateTime {
        return DateTime(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: DateTime): Long {
        return date.millis
    }
}