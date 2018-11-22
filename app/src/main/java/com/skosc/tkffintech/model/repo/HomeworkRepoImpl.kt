package com.skosc.tkffintech.model.repo

import android.content.SharedPreferences
import com.skosc.tkffintech.misc.DataUpdateResult
import com.skosc.tkffintech.model.entity.ExpirationTimer
import com.skosc.tkffintech.model.room.CourseInfoDao
import com.skosc.tkffintech.model.room.GradesDao
import com.skosc.tkffintech.model.room.HomeworkDao
import com.skosc.tkffintech.model.room.UserDao
import com.skosc.tkffintech.model.service.NetworkInfoService
import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import com.skosc.tkffintech.model.webservice.TinkoffGradesApi
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Response

class HomeworkRepoImpl(
        private val gradesApi: TinkoffGradesApi,
        private val cursesApi: TinkoffCursesApi,
        private val courseInfoDao: CourseInfoDao,
        private val gradesDao: GradesDao,
        private val userDao: UserDao,
        private val homeworkDao: HomeworkDao,
        private val timersSharedPreferences: SharedPreferences,
        private val networkInfo: NetworkInfoService
) : HomeworkRepo {
    companion object {
        private const val UPDATE_TIME_POLITIC_SECONDS = 60 * 60
    }

    private val cdisp = CompositeDisposable()
    private val dataRefresh = HashMap<String, ExpirationTimer>()


    private val dataFreshUtil = ExpirationTimer.create(timersSharedPreferences, "homework-update")

    override fun tryForceRefresh(course: String): Single<DataUpdateResult> {
        TODO()
    }

    override fun checkForUpdates(course: String): Single<DataUpdateResult> {
        return needsUpdate(course).flatMap {
            if (it) {
                tryForceRefresh(course)
            } else {
                Single.just(DataUpdateResult.NotUpdated)
            }
        }
    }

    private fun needsUpdate(course: String): Single<Boolean> {
        if (course in dataRefresh) {
            return dataRefresh[course]!!.isExpired
        }

        val key = makeRefreshKey(course)
        dataRefresh[course] = ExpirationTimer.create(timersSharedPreferences, key)
        return Single.just(true)
    }

    private fun makeRefreshKey(course: String): String {
        return "homework-refresh-$course"
    }

    private fun successToUpdateResult(resp: Response<*>): DataUpdateResult {
        return if (resp.isSuccessful) {
            DataUpdateResult.Updated
        } else {
            DataUpdateResult.Error
        }
    }
}