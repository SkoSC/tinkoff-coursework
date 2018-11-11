package com.skosc.tkffintech.model.room.adapter

import androidx.room.TypeConverter
import com.skosc.tkffintech.entities.HomeworkStatus
import org.joda.time.DateTime


class HomeworkStatusAdapter {
    @TypeConverter
    fun fromStatus(value: Int): HomeworkStatus {
        return when(value) {
            1 -> HomeworkStatus.NEW
            2 -> HomeworkStatus.ACCEPTED
            else -> HomeworkStatus.UNKNOWN
        }
    }

    @TypeConverter
    fun statusToInt(date: HomeworkStatus): Int {
        return when(date) {
            HomeworkStatus.NEW -> 1
            HomeworkStatus.ACCEPTED -> 2
            else -> 0
        }
    }
}