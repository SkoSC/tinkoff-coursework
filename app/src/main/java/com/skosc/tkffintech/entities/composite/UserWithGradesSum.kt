package com.skosc.tkffintech.entities.composite

import androidx.room.Embedded
import com.skosc.tkffintech.entities.User

data class UserWithGradesSum(
        @Embedded
        val user: User,
        val gradesSum: Double
)