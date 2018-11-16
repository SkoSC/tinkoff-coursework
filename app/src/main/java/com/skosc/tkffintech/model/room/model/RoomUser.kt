package com.skosc.tkffintech.model.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skosc.tkffintech.entities.User

@Entity(tableName = "user")
data class RoomUser(
        @PrimaryKey
        @ColumnInfo(name = "student_id")
        val id: Long,

        @ColumnInfo(name = "name")
        val name: String
) {
    companion object {
        fun form(user: User) = RoomUser(user.id, user.name)
    }

    fun convert(): User = User(id, name)
}