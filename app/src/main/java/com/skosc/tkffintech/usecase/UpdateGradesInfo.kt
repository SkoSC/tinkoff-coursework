package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.entities.HomeworkTask
import com.skosc.tkffintech.model.room.GradesDao
import com.skosc.tkffintech.model.room.HomeworkDao
import com.skosc.tkffintech.model.room.UserDao
import com.skosc.tkffintech.model.room.model.*
import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import com.skosc.tkffintech.model.webservice.TinkoffGradesApi
import io.reactivex.Single
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

    fun perform(curse: String) {
        Single.zip(cursesApi.homeworks(curse), gradesApi.gradesForCourse(curse), zippper)
                .subscribe { it ->
                    val homewroks = it.hw.homeworks.map { it.convert(curse) }
                    val grades = it.gr.flatMap { it.grades() }
                    homewroks.forEach { homework ->
                        val tasksIdSet = homework.tasks.map { it.id }.toSet()
                        val grades = grades.filter { it.taskId in tasksIdSet }

                        val roomTasks = homework.tasks.map { RoomHomeworkTask.from(it, homework.id) }
                        val roomHomework = RoomHomework.from(homework)
                        homeworkDao.insert(RoomHomeworkAndTasks(roomHomework, roomTasks))

                        val users = grades.map { it.user }
                        val roomUsers = users.map { RoomUser.form(it) }
                        userDao.insertOrUpdate(roomUsers)

                        val roomGrades = grades.map { RoomGrade.from(it) }
                        gradesDao.insert(roomGrades)
                    }
                }

    }
}