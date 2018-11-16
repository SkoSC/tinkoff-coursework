package com.skosc.tkffintech.viewmodel.grades

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.usecase.LoadGrades
import com.skosc.tkffintech.usecase.LoadUsersForCourse
import com.skosc.tkffintech.utils.own
import com.skosc.tkffintech.viewmodel.HomeworkWithGrades
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject

class GradesSingleUserViewModelImpl(private val loadGrades: LoadGrades, private val loadUsers: LoadUsersForCourse) : GradesSingleUserViewModel() {
    private var course: String = ""
    private val gradesSubject = BehaviorSubject.create<List<HomeworkWithGrades>>()

    override val grades: MutableLiveData<List<HomeworkWithGrades>> = MutableLiveData()
    override val users: MutableLiveData<List<User>> = MutableLiveData()

    init {
        cdisp own gradesSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { grades.value = it }
    }

    override fun init(course: String) {
        this.course = course
        // TODO Should i use custom disposable
        cdisp own loadUsers.load(course)
                .first(listOf())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it -> users.value = it }
    }

    override fun setUser(user: User) {
        cdisp own loadGrades.loadGrades(user.id,"android_fall2018").map {
            it.map { HomeworkWithGrades(it.first, it.second.map { it.task to it.grade }) }
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    grades.value = it
                }
    }
}