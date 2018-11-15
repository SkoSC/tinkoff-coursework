package com.skosc.tkffintech.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skosc.tkffintech.model.room.model.RoomUser
import io.reactivex.Observable

@Dao
abstract class UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrUpdate(users: List<RoomUser>)

    @Query("SELECT * FROM `user` WHERE `student_id` == :id LIMIT 1")
    abstract fun findById(id: Long): Observable<RoomUser>
}