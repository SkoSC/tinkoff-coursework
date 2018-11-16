package com.skosc.tkffintech.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.model.room.model.RoomGrade
import com.skosc.tkffintech.model.room.model.RoomUserWithGradesSum
import com.skosc.tkffintech.viewmodel.UserWithGradesSum
import io.reactivex.Observable

@Dao
abstract class GradesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(grades: List<RoomGrade>)

    @Query("SELECT * FROM `grade` WHERE `task_id_fk` == :task AND `user_id_fk` == :user LIMIT 1")
    abstract fun gradesForUserByTask(user: Long, task: Long): Observable<RoomGrade>

    @Query("""
        SELECT user.*, SUM(grade.mark) AS gradesSum FROM user
        LEFT JOIN grade ON grade.user_id_fk == user.student_id
        LEFT JOIN user_course_rel ON user_course_rel.user_id == user.student_id
        WHERE user_course_rel.course_url == :course
        GROUP BY user.student_id
    """)
    abstract fun gradesTotalForUsersWithCourse(course: String): Observable<List<RoomUserWithGradesSum>>
}