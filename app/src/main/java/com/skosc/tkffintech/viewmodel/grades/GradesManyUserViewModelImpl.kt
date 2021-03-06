package com.skosc.tkffintech.viewmodel.grades

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.R
import com.skosc.tkffintech.entities.composite.UserWithGradesSum
import com.skosc.tkffintech.misc.model.ItemSorter
import com.skosc.tkffintech.usecase.LoadCourseStatistics
import com.skosc.tkffintech.usecase.LoadHomeworks
import com.skosc.tkffintech.utils.extensions.own
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class GradesManyUserViewModelImpl(
        private val course: String,
        private val courseStatistics: LoadCourseStatistics,
        loadHomeworks: LoadHomeworks
) : GradesManyUserViewModel() {
    private var currentSorter: ItemSorter<UserWithGradesSum>
    private val gradesSubject = BehaviorSubject.createDefault<List<UserWithGradesSum>>(listOf())

    override val sorters: MutableLiveData<List<ItemSorter<UserWithGradesSum>>> = MutableLiveData()
    override val userWithGradesSum: MutableLiveData<List<UserWithGradesSum>> = MutableLiveData()

    init {
        sorters.value = listOf(
                ItemSorter(R.string.sort_descending, java.util.Comparator { o1, o2 ->
                    o2.gradesSum.compareTo(o1.gradesSum)
                }),
                ItemSorter(R.string.sort_ascending, java.util.Comparator { o1, o2 ->
                    o1.gradesSum.compareTo(o2.gradesSum)
                }),
                ItemSorter(R.string.sort_by_name, java.util.Comparator { o1, o2 -> o1.user.name.compareTo(o2.user.name) })
        )

        currentSorter = sorters.value!![0]

        cdisp own loadHomeworks.checkForUpdates(course).subscribe { success ->
            cdisp own gradesSubject
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({ grades ->
                        userWithGradesSum.value = grades.sortedWith(currentSorter.comparator)
                    }, {})

            cdisp own courseStatistics.gradeSumForUserOnCourse(course)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({ data -> gradesSubject.onNext(data) }, {})
        }
    }

    override fun setSorter(sorter: ItemSorter<UserWithGradesSum>) {
        currentSorter = sorter
        gradesSubject.onNext(gradesSubject.value!!)
    }
}