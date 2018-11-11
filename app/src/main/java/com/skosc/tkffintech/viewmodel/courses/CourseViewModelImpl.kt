package com.skosc.tkffintech.viewmodel.courses

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.model.repo.CourseRepo
import com.skosc.tkffintech.utils.own
import io.reactivex.android.schedulers.AndroidSchedulers

class CourseViewModelImpl(repo: CourseRepo) : CourseViewModel() {
    override val courses: MutableLiveData<List<CourseInfo>> = MutableLiveData()

    init {
        cdisp own repo.courses
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    courses.value = it
                }
    }
}