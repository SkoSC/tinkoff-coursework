package com.skosc.tkffintech.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.model.room.model.RoomGrade
import io.reactivex.Observable

@Dao
abstract class GradesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(grades: List<RoomGrade>)

    @Query("SELECT * FROM `grade` WHERE `task_id_fk` == :task AND `user_id_fk` == :user LIMIT 1")
    abstract fun gradesForUserByTask(user: Long, task: Long): Observable<RoomGrade>
}