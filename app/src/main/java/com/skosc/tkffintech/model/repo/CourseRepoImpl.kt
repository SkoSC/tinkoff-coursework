package com.skosc.tkffintech.model.repo

import android.content.SharedPreferences
import android.util.Log
import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.misc.DataUpdateResult
import com.skosc.tkffintech.model.entity.ExpirationTimer
import com.skosc.tkffintech.model.room.CourseInfoDao
import com.skosc.tkffintech.model.room.UserDao
import com.skosc.tkffintech.model.room.allBusiness
import com.skosc.tkffintech.model.room.model.RoomCourseInfo
import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import com.skosc.tkffintech.service.NetworkInfoService
import com.skosc.tkffintech.utils.own
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.joda.time.DateTime
import retrofit2.Response

class CourseRepoImpl(
        private val api: TinkoffCursesApi,
        private val cursesDao: CourseInfoDao,
        private val userDao: UserDao,
        private val timerSharedPreferences: SharedPreferences,
        private val networkInfo: NetworkInfoService
) : CourseRepo {
    companion object {
        private const val UPDATE_TIME_POLITIC_SECONDS = 60 * 60 * 12
        private const val TAG = "TKF_REPO_COURSE"
    }

    private val cdisp = CompositeDisposable()
    private val expTimer = ExpirationTimer.create(timerSharedPreferences, "course-info-update")
    override val courses: Observable<List<CourseInfo>> get() = cursesDao.allBusiness()

    override fun tryForceUpdate(): Single<Boolean> {
        return api.connections()
                .doAfterSuccess { courses ->
                    val nextUpdateTime = DateTime.now().plusSeconds(UPDATE_TIME_POLITIC_SECONDS)
                    expTimer.rewind(nextUpdateTime)
                    saveCoursesToDb(courses)
                }
                .map {
                    it.isSuccessful
                }
    }

    private fun saveCoursesToDb(courses: Response<TinkoffCursesApi.ConnectionsResp>) {
        if (!courses.isSuccessful) {
            throw IllegalStateException("Network request failed")
        }

        val it = courses.body()!!
        val roomCourses = it.courses.map { it.convert() }.map { RoomCourseInfo.from(it) }
        cursesDao.insert(roomCourses)
    }

    override fun usersWithCourse(course: String): Observable<List<User>> {
        performSoftUpdate()
        return userDao.usersForCourse(course).map { it.map { it.convert() } }
    }

    private fun softUpdate(): Single<DataUpdateResult> {
        return expTimer.isExpired.map { it && networkInfo.checkConnection() }.flatMap { needsUpdate ->
            if (needsUpdate) {
                tryForceUpdate().map(this::successToUpdateResult)
            } else {
                Single.just(DataUpdateResult.NotUpdated)
            }
        }
    }

    private fun successToUpdateResult(success: Boolean): DataUpdateResult {
        return if (success) {
            DataUpdateResult.Updated
        } else {
            DataUpdateResult.Error
        }
    }

    override fun performSoftUpdate() {
        cdisp own softUpdate().subscribe { res -> Log.d(TAG, "Update result: $res") }
    }
}