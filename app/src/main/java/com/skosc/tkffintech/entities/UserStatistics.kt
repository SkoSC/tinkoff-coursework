package com.skosc.tkffintech.entities

import com.skosc.tkffintech.misc.Ratio

data class UserStatistics(
        val totalScore: Double,
        val testsCount: Int,
        val coursesCount: Int
)