package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.CourseInfo
import io.reactivex.Observable

interface CourseRepo {
    val courses: Observable<List<CourseInfo>>
    fun tryToUpdateAll()
}