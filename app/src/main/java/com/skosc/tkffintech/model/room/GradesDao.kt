package com.skosc.tkffintech.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.skosc.tkffintech.model.room.model.RoomGrade

@Dao
abstract class GradesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(grades: List<RoomGrade>)
}