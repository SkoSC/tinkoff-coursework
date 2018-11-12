package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.entities.HomeworkTask
import com.skosc.tkffintech.model.room.GradesDao
import com.skosc.tkffintech.model.room.HomeworkDao
import com.skosc.tkffintech.model.room.UserDao
import com.skosc.tkffintech.model.room.model.*

class UpdateGradesInfo(
        private val gradesDao: GradesDao,
        private val userDao: UserDao,
        private val homeworkDao: HomeworkDao
) {
    fun update(grades: List<HomeworkGrade>, homework: Homework) {
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