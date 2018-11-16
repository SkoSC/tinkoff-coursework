package com.skosc.tkffintech.model.room.model

import androidx.room.ColumnInfo
import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.viewmodel.UserWithGradesSum

data class RoomUserWithGradesSum(
        @ColumnInfo(name = "student_id") val userId: Long,
        val name: String,
        val gradesSum: Double
) {
    fun convert(): UserWithGradesSum = UserWithGradesSum(
            User(userId, name), gradesSum
    )
}