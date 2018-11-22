package com.skosc.tkffintech.model.repo

import android.content.SharedPreferences
import com.skosc.tkffintech.daoModule
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
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTime
import retrofit2.Response

class CourseRepoImplV2(
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

    private val dataFreshUtil = ExpirationTimer.create(timerSharedPreferences, "v2-course-data-refresh")

    override val courses: Observable<List<CourseInfo>> = cursesDao.allBusiness()

    override fun tryForceUpdate(): Single<Boolean> {
        return api.connections()
                .doAfterSuccess { courses ->
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
        val nextUpdateTime = DateTime.now().plusSeconds(UPDATE_TIME_POLITIC_SECONDS)
        dataFreshUtil.rewind(nextUpdateTime)
    }

    override fun usersWithCourse(course: String): Observable<List<User>> {
        performSoftUpdate()
        return userDao.usersForCourse(course).map { it.map { it.convert() } }
    }

    override fun performSoftUpdate() {
        softUpdate().subscribe()
    }

    private fun softUpdate() : Single<DataUpdateResult> {
        return needsUpdate.flatMap { needsUpdate ->
            if (needsUpdate) {
                tryForceUpdate().map { if (it) DataUpdateResult.Updated else DataUpdateResult.Error }
            } else {
                Single.just(DataUpdateResult.NotUpdated)
            }
        }
    }

    private val needsUpdate
        get() = dataFreshUtil.isExpired
                .flatMap { isExpired ->
                    cursesDao.count()
                            .first(0)
                            .map { count -> count == 0 || isExpired }
                }

}