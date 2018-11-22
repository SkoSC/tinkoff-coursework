package com.skosc.tkffintech.viewmodel.grades

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.utils.own
import com.skosc.tkffintech.viewmodel.HomeworkWithGrades
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject

class GradesSingleUserViewModelImpl(private val course: String) : GradesSingleUserViewModel() {
    private val gradesSubject = BehaviorSubject.create<List<HomeworkWithGrades>>()

    override val grades: MutableLiveData<List<HomeworkWithGrades>> = MutableLiveData()
    override val users: MutableLiveData<List<User>> = MutableLiveData()

    init {
        cdisp own gradesSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { grades.value = it }
    }

    override fun setUser(user: User) {
        grades.value = listOf()
    }
}