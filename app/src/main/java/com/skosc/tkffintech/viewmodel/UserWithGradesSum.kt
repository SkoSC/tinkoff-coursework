package com.skosc.tkffintech.viewmodel

import androidx.room.Embedded
import com.skosc.tkffintech.entities.User

data class UserWithGradesSum(
        @Embedded
        val user: User,
        val gradesSum: Double
)