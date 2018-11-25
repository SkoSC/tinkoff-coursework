package com.skosc.tkffintech.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skosc.tkffintech.model.room.model.RoomCourseInfo
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface CourseInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<RoomCourseInfo>)

    @Query("SELECT * FROM `course_info`")
    fun all(): Single<List<RoomCourseInfo>>

    @Query("SELECT COUNT(*) FROM course_info")
    fun count(): Observable<Int>
}
