package com.skosc.tkffintech.model.room.adapter

import androidx.room.TypeConverter
import com.skosc.tkffintech.entities.HomeworkTaskType


class HomeworkTaskTypeAdapter {
    @TypeConverter
    fun fromType(value: Int): HomeworkTaskType {
        return when (value) {
            1 -> HomeworkTaskType.TEST
            2 -> HomeworkTaskType.FULL
            else -> HomeworkTaskType.UNKNOWN
        }
    }

    @TypeConverter
    fun typeToInt(date: HomeworkTaskType): Int {
        return when (date) {
            HomeworkTaskType.TEST -> 1
            HomeworkTaskType.FULL -> 2
            else -> 0
        }
    }
}