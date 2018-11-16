package com.skosc.tkffintech.viewmodel.coursedetail

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.usecase.LoadGrades
import com.skosc.tkffintech.utils.own
import com.skosc.tkffintech.viewmodel.HomeworkWithGrades
import io.reactivex.android.schedulers.AndroidSchedulers

class CourseDetailViewModelImpl(private val loader: LoadGrades) : CourseDetailViewModel() {
    override val grades: MutableLiveData<List<HomeworkWithGrades>> = MutableLiveData()

    init {
        cdisp own loader.loadGradesForCurrentUser("android_fall2018").map {
            it.map { HomeworkWithGrades(it.first, it.second.map { it.task to it.grade }) }
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    grades.value = it
                }

    }
}