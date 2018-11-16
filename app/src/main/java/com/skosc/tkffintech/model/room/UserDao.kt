package com.skosc.tkffintech.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skosc.tkffintech.model.room.model.RoomUser
import com.skosc.tkffintech.model.room.model.RoomUserCourseRelation
import io.reactivex.Observable

@Dao
abstract class UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrUpdate(users: List<RoomUser>)

    @Query("SELECT * FROM `user` WHERE `student_id` == :id LIMIT 1")
    abstract fun findById(id: Long): Observable<RoomUser>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertCourseUserRelations(realtions: List<RoomUserCourseRelation>)

    @Query("SELECT user.* FROM user JOIN  user_course_rel WHERE user_course_rel.course_url == :course AND user_course_rel.user_id == user.student_id")
    abstract fun usersForCourse(course: String): Observable<List<RoomUser>>
}