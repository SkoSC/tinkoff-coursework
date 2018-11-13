package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.dao.UserInfoDao
import com.skosc.tkffintech.model.room.CourseInfoDao
import com.skosc.tkffintech.model.room.UserDao
import com.skosc.tkffintech.model.room.model.RoomCourseInfo
import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import com.skosc.tkffintech.utils.mapEach

class UpdateCourseList(val courseInfoDao: CourseInfoDao, val api: TinkoffCursesApi) {
    fun perform() {
        api.connections()
                .map { it.courses.map { it.convert() } }
                .map { it.map { RoomCourseInfo.from(it) } }
                .subscribe { infos ->
                    courseInfoDao.insert(infos)
                }
    }
}