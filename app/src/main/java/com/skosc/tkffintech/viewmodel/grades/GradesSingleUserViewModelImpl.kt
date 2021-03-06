package com.skosc.tkffintech.viewmodel.grades

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.entities.composite.HomeworkWithGrades
import com.skosc.tkffintech.model.repo.CourseRepo
import com.skosc.tkffintech.usecase.LoadHomeworks
import com.skosc.tkffintech.utils.extensions.own
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class GradesSingleUserViewModelImpl(
        private val course: String,
        private val loadHomeworks: LoadHomeworks,
        private val courseRepo: CourseRepo
) : GradesSingleUserViewModel() {
    private val gradesSubject = BehaviorSubject.create<List<HomeworkWithGrades>>()

    override val grades: MutableLiveData<List<HomeworkWithGrades>> = MutableLiveData()
    override val users: MutableLiveData<List<User>> = MutableLiveData()

    init {
        cdisp own loadHomeworks.checkForUpdates(course).subscribe { success ->
            cdisp own gradesSubject
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe ({ grades.value = it }, {})

            cdisp own courseRepo.usersWithCourse(course)
                    .map { it.sortedBy { it.name } }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe ({ freshUsers -> users.value = freshUsers }, {})
        }
    }

    override fun setUser(user: User) {
        grades.value = listOf()
        cdisp own loadHomeworks.gradesForUser(user.id, course)
                .subscribeOn(Schedulers.io())
                .subscribe ({ data ->
                    gradesSubject.onNext(data)
                }, {})
    }
}