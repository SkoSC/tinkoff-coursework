package com.skosc.tkffintech.entities

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class User(
        val id: Long,
        val name: String
)