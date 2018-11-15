package com.skosc.tkffintech.model.room

import androidx.room.*
import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.model.room.model.RoomHomework
import com.skosc.tkffintech.model.room.model.RoomHomeworkAndTasks
import com.skosc.tkffintech.model.room.model.RoomHomeworkTask
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

    @Query("SELECT * FROM `homework` WHERE `course` == :course")
    abstract fun homeworksForCourse(course: String): Observable<List<RoomHomeworkAndTasks>>

    @Transaction
    open fun insert(homework: RoomHomeworkAndTasks) {
        _insertHomework(homework.homework)
        _insertHomeworkTasks(homework.tasks)
    }
}