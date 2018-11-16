package com.skosc.tkffintech.viewmodel.grades

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.R
import com.skosc.tkffintech.usecase.LoadGrades
import com.skosc.tkffintech.misc.ItemSorter
import com.skosc.tkffintech.utils.own
import com.skosc.tkffintech.viewmodel.UserWithGradesSum
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject

class GradesManyUserViewModelImpl(private val gradesLoader: LoadGrades) : GradesManyUserViewModel() {
    private var course: String = ""
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
    }

    override fun init(course: String) {
        this.course = course
        gradesLoader.loadGradesSumForAllCourse(course)
                .distinct()
                .subscribe(gradesSubject)
        cdisp own gradesSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { grades ->
                    userWithGradesSum.value = grades.sortedWith(currentSorter.comparator)
                }
    }

    override fun setSorter(sorter: ItemSorter<UserWithGradesSum>) {
        currentSorter = sorter
        gradesSubject.onNext(gradesSubject.value!!)
    }
}