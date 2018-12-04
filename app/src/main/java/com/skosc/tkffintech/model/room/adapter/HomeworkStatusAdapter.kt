package com.skosc.tkffintech.model.room.adapter

import androidx.room.TypeConverter
import com.skosc.tkffintech.entities.HomeworkStatus


class HomeworkStatusAdapter {
    @TypeConverter
    fun fromStatus(value: Int): HomeworkStatus {
        return when (value) {
            1 -> HomeworkStatus.NEW
            2 -> HomeworkStatus.ACCEPTED
            3 -> HomeworkStatus.TEST_RESULT
            4 -> HomeworkStatus.FAILED
            else -> HomeworkStatus.UNKNOWN
        }
    }

    @TypeConverter
    fun statusToInt(date: HomeworkStatus): Int {
        return when (date) {
            HomeworkStatus.NEW -> 1
            HomeworkStatus.ACCEPTED -> 2
            HomeworkStatus.TEST_RESULT -> 3
            HomeworkStatus.FAILED -> 4
            else -> 0
        }
    }
}