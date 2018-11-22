package com.skosc.tkffintech.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skosc.tkffintech.model.room.model.RoomGrade
import com.skosc.tkffintech.model.room.model.RoomHomworkToTasks
import com.skosc.tkffintech.model.room.model.RoomUserWithGradesSum
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

    @Query("SELECT SUM(grade.mark) FROM grade WHERE grade.user_id_fk == :user")
    abstract fun totalScoreOfUser(user: Long): Observable<Double>

    @Query("SELECT * FROM grade WHERE grade.user_id_fk == :user")
    abstract fun allGradesOfUser(user: Long): Observable<List<RoomGrade>>


    @Query("""
        SELECT grade.* FROM grade
        LEFT JOIN homework_task ON homework_task.contest_id == grade.task_id_fk
        WHERE (
	        homework_task.contest_type == 1
		        AND
	        grade.user_id_fk == :user
        )
        """)
    abstract fun testGradesForUser(user: Long): Observable<List<RoomGrade>>

    @Query("""
        SELECT grade.* FROM grade
        LEFT JOIN homework_task ON homework_task.contest_id == grade.task_id_fk
        LEFT JOIN homework ON homework.homework_id == homework_task.homework_id_fk
        WHERE (
          homework.course = :course
	        AND
          grade.user_id_fk == :user
        )
    """)
    abstract fun gradesForUserOnCourse(user: Long, course: String): Observable<List<RoomGrade>>

    @Query("""
        SELECT homework.*, homework_task.*, grade.* FROM user
        LEFT JOIN homework ON homework.course == :course
        LEFT JOIN homework_task ON homework_task.homework_id_fk == homework.homework_id
        LEFT JOIN grade ON grade.user_id_fk == user.student_id AND grade.task_id_fk == homework_task.contest_id
        WHERE user.student_id == :user AND homework.homework_id IS NOT NULL AND homework_task.id IS NOT NULL AND grade.grade_id IS NOT NULL
    """)
    abstract fun gradesWithHomework(user: Long, course: String): Observable<List<RoomHomworkToTasks>>
}