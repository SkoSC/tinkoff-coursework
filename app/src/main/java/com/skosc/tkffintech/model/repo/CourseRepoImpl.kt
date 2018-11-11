package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import io.reactivex.Observable

class CourseRepoImpl(private val api: TinkoffCursesApi) : CourseRepo {
    override val courses: Observable<List<CourseInfo>> get() = api.connections()
            .toObservable()
            .map { it.courses.map { it.convert() } }
}