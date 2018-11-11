package com.skosc.tkffintech.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "homework")
data class RoomHomework(
        @PrimaryKey
        @ColumnInfo(name = "homework_id")
        val id: Long,

        @ColumnInfo(name = "title")
        val title: String,

        @ColumnInfo(name = "course")
        val course: String
)