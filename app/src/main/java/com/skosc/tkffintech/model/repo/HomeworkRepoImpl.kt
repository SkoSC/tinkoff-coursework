package com.skosc.tkffintech.model.repo

import android.content.SharedPreferences
import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.entities.TaskWithGrade
import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.misc.DataUpdateResult
import com.skosc.tkffintech.model.entity.ExpirationTimer
import com.skosc.tkffintech.model.room.*
import com.skosc.tkffintech.model.room.model.*
import com.skosc.tkffintech.model.service.NetworkInfoService
import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import com.skosc.tkffintech.model.webservice.TinkoffGradesApi
import com.skosc.tkffintech.utils.mapEach
import com.skosc.tkffintech.utils.own
import com.skosc.tkffintech.viewmodel.UserWithGradesSum
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.joda.time.DateTime

class HomeworkRepoImpl(
        private val gradesApi: TinkoffGradesApi,
        private val cursesApi: TinkoffCursesApi,
        private val courseInfoDao: CourseInfoDao,
        private val gradesDao: GradesDao,
        private val userDao: UserDao,
        private val homeworkDao: HomeworkDao,
        timersSharedPreferences: SharedPreferences,
        private val networkInfo: NetworkInfoService
) : HomeworkRepo {
    companion object {
        private const val UPDATE_TIME_POLITIC_SECONDS = 60 * 60
    }

    private val cdisp = CompositeDisposable()
    private val expTimer = ExpirationTimer.create(timersSharedPreferences, "homework-update")


}