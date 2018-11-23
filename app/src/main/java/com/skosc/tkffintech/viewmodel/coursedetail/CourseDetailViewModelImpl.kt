package com.skosc.tkffintech.viewmodel.coursedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.misc.UpdateResult
import com.skosc.tkffintech.usecase.LoadHomeworks
import com.skosc.tkffintech.utils.own
import com.skosc.tkffintech.viewmodel.HomeworkWithGrades
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CourseDetailViewModelImpl(
        private val course: String,
        private val loadHomeworks: LoadHomeworks
) : CourseDetailViewModel() {
    override val grades: MutableLiveData<List<HomeworkWithGrades>> = MutableLiveData()
    override val statsScore: MutableLiveData<Double> = MutableLiveData()
    override val statsTests: MutableLiveData<Int> = MutableLiveData()
    override val statsHomeWorks: MutableLiveData<Int> = MutableLiveData()

    override fun checkForUpdates(): LiveData<UpdateResult> {
        val indicator = MutableLiveData<UpdateResult>()
        cdisp own loadHomeworks.checkForUpdates(course)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ success ->
                    indicator.value = success
                    pushGrades()
                }, {
                    indicator.value = UpdateResult.Error
                })
        return indicator
    }

    override fun forceRefresh(): LiveData<UpdateResult> {
        val indicator = MutableLiveData<UpdateResult>()
        cdisp own loadHomeworks.tryLoadHomewroksFromNetwork(course)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ success ->
                    indicator.value = success
                    pushGrades()
                }, {
                    indicator.value = UpdateResult.Error
                })
        return indicator
    }

    private fun pushGrades() {
        cdisp own loadHomeworks.gradesForCurrentUser(course)
                .subscribeOn(Schedulers.io())
                .delay(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { freshGrades -> grades.value = freshGrades }
    }
}