package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.model.room.CourseInfoDao
import com.skosc.tkffintech.model.room.allBusiness
import com.skosc.tkffintech.model.room.model.RoomCourseInfo
import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import com.skosc.tkffintech.usecase.UpdateCourseList
import io.reactivex.Observable

class CourseRepoImpl(
        private val api: TinkoffCursesApi,
        private val cursesDao: CourseInfoDao
) : CourseRepo {
    override fun tryToUpdateAll() {
        api.connections()
                .map { it.courses.map { it.convert() } }
                .map { it.map { RoomCourseInfo.from(it) } }
                .subscribe { infos ->
                    cursesDao.insert(infos)
                }
    }

    override val courses: Observable<List<CourseInfo>> get() = cursesDao.allBusiness()
}