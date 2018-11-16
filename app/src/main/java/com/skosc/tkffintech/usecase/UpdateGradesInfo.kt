package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.room.GradesDao
import com.skosc.tkffintech.model.room.HomeworkDao
import com.skosc.tkffintech.model.room.UserDao
import com.skosc.tkffintech.model.room.model.*
import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import com.skosc.tkffintech.model.webservice.TinkoffGradesApi
import io.reactivex.functions.BiFunction

class UpdateGradesInfo(
        private val gradesDao: GradesDao,
        private val userDao: UserDao,
        private val homeworkDao: HomeworkDao,
        private val cursesApi: TinkoffCursesApi,
        private val gradesApi: TinkoffGradesApi
) {
    data class PR(val hw: TinkoffCursesApi.HomeworksResp, val gr: List<TinkoffGradesApi.GradesResp>)

    val zippper = BiFunction<TinkoffCursesApi.HomeworksResp, List<TinkoffGradesApi.GradesResp>, PR> { hw, gr ->
        PR(hw, gr)
    }

    fun perform(course: String) {
        gradesApi.gradesForCourse(course).subscribe { resp ->
            val students = resp.flatMap { it.students }
            val roomUsers = students.map { RoomUser(it.id, it.name) }
            userDao.insertOrUpdate(roomUsers)

            val courseRelation = roomUsers.map { RoomUserCourseRelation(0, it.id, course) }
            userDao.insertCourseUserRelations(courseRelation)

            val grades = resp.flatMap { it.grades() }.map { RoomGrade.from(it) }
            gradesDao.insert(grades)
            cursesApi.homeworks(course).subscribe { homeworks ->
                val homeworks = homeworks.homeworks.map { it.convert(course) }.map { hw ->
                    RoomHomeworkAndTasks(RoomHomework.from(hw), hw.tasks.map { RoomHomeworkTask.from(it, hw.id) })
                }
                homeworks.forEach { homeworkDao.insert(it) }
            }
        }
    }
}