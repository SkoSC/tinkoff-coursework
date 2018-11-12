package com.skosc.tkffintech.viewmodel.courses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.model.repo.CourseRepo
import com.skosc.tkffintech.model.repo.HomeworkRepo
import com.skosc.tkffintech.utils.own
import io.reactivex.android.schedulers.AndroidSchedulers

class CourseViewModelImpl(repo: CourseRepo, homeworkRepo: HomeworkRepo) : CourseViewModel() {
    override val activeCourses: MutableLiveData<CourseInfo> = MutableLiveData()
    override val allCourses: MutableLiveData<List<CourseInfo>> = MutableLiveData()

    init {
        cdisp own repo.courses
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    allCourses.value = it
                }
    }
}