package com.skosc.tkffintech.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.model.room.model.RoomCourseInfo
import io.reactivex.Observable

@Dao
interface CourseInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<RoomCourseInfo>)

    @Query("SELECT * FROM `course_info`")
    fun all(): Observable<List<RoomCourseInfo>>
}

fun CourseInfoDao.allBusiness(): Observable<List<CourseInfo>> = this.all()
        .map { it.map { it.convert() } }
