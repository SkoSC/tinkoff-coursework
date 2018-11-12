package com.skosc.tkffintech.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.skosc.tkffintech.model.room.model.RoomUser

@Dao
abstract class UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrUpdate(users: List<RoomUser>)
}