package com.skosc.tkffintech.viewmodel.coursedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.model.repo.HomeworkRepo
import com.skosc.tkffintech.utils.own
import com.skosc.tkffintech.viewmodel.RxViewModel

class CourseDetailViewModelImpl(private val homeWorkRepo: HomeworkRepo) : CourseDetailViewModel() {
    override val homeworks: MutableLiveData<List<Homework>> = MutableLiveData()

    init {
        cdisp own homeWorkRepo.homeworks("android_fall2018")
                .subscribe { homeworks.postValue(it) }
    }

}