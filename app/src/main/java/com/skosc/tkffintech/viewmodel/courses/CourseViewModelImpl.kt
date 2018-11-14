package com.skosc.tkffintech.viewmodel.courses

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.usecase.LoadCourses
import com.skosc.tkffintech.utils.own
import io.reactivex.android.schedulers.AndroidSchedulers

class CourseViewModelImpl(private val loadCourses: LoadCourses) : CourseViewModel() {
    override val activeCourses: MutableLiveData<CourseInfo> = MutableLiveData()
    override val allCourses: MutableLiveData<List<CourseInfo>> = MutableLiveData()

    init {
        cdisp own loadCourses.allCourses
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    allCourses.value = it
                }
    }

    override fun forceUpdate() {
        loadCourses.tryLoadCourses()
    }
}