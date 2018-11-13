package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.model.room.CourseInfoDao
import com.skosc.tkffintech.model.room.allBusiness
import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import com.skosc.tkffintech.usecase.UpdateCourseList
import io.reactivex.Observable

class CourseRepoImpl(
        private val updateCourseList: UpdateCourseList,
        private val cursesDao: CourseInfoDao
) : CourseRepo {
    override val courses: Observable<List<CourseInfo>> get() = cursesDao.allBusiness()
}