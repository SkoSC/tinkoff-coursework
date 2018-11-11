package com.skosc.tkffintech.model.room

import androidx.room.*
import io.reactivex.Observable

@Dao
abstract class HomeworkDao {
    @Query("SELECT * FROM `homework`")
    abstract fun all(): Observable<List<RoomHomeworkAndTasks>>

    @Query("DELETE FROM `homework`")
    abstract fun deleteAllHomeworks()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun _insertHomework(hw: RoomHomework)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun _insertHomeworkTasks(tasks: List<RoomHomeworkTask>)

    @Transaction
    open fun insert(homework: RoomHomeworkAndTasks) {
        _insertHomework(homework.homework)
        _insertHomeworkTasks(homework.tasks)
    }
}