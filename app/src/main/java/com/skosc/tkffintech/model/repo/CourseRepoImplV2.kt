package com.skosc.tkffintech.model.repo

import android.content.SharedPreferences
import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.misc.model.UpdateResult
import com.skosc.tkffintech.model.entity.ExpirationTimer
import com.skosc.tkffintech.model.room.CourseInfoDao
import com.skosc.tkffintech.model.room.UserDao
import com.skosc.tkffintech.model.room.model.RoomCourseInfo
import com.skosc.tkffintech.model.room.model.RoomUser
import com.skosc.tkffintech.model.service.NetworkInfoService
import com.skosc.tkffintech.model.webservice.model.ConnectionsResp
import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import com.skosc.tkffintech.utils.extensions.extractUpdateResult
import com.skosc.tkffintech.utils.extensions.mapEach
import io.reactivex.Single
import org.joda.time.DateTime
import retrofit2.Response

class CourseRepoImplV2(
        private val api: TinkoffCursesApi,
        private val coursesDao: CourseInfoDao,
        private val userDao: UserDao,
        private val timerSharedPreferences: SharedPreferences,
        private val networkInfo: NetworkInfoService
) : CourseRepo {
    companion object {
        private const val UPDATE_TIME_POLITIC_SECONDS = 60 * 60 * 12
        private const val TAG = "TKF_REPO_COURSE"
    }

    private val dataFreshUtil = ExpirationTimer.create(timerSharedPreferences, "v2-course-data-refresh")

    override val courses: Single<List<CourseInfo>>
        get() = coursesDao.all().mapEach(RoomCourseInfo::convert)

    override fun checkForUpdates(): Single<UpdateResult> {
        return needsUpdate.flatMap {
            if (it) {
                tryForceRefresh()
            } else {
                Single.just(UpdateResult.NotUpdated)
            }
        }
    }

    override fun tryForceRefresh(): Single<UpdateResult> {
        return api.connections()
                .doAfterSuccess { events ->
                    val nextUpdateTime = DateTime.now().plusSeconds(UPDATE_TIME_POLITIC_SECONDS)
                    dataFreshUtil.rewind(nextUpdateTime)
                    saveCoursesToDb(events)
                }.map(Response<*>::extractUpdateResult)
    }

    override fun usersWithCourse(course: String): Single<List<User>> {
        return userDao.usersForCourse(course).mapEach(RoomUser::convert)
    }

    private fun saveCoursesToDb(events: Response<ConnectionsResp>) {
        if (!events.isSuccessful) {
            throw IllegalStateException("Network request failed")
        }
        val courses = events.body()!!.courses
                .map { it.convert() }
                .map(RoomCourseInfo.Companion::from)
        coursesDao.insert(courses)

    }

    private val needsUpdate
        get() = dataFreshUtil.isExpired
                .flatMap { isExpired ->
                    coursesDao.count()
                            .map { count -> count == 0 || isExpired }
                }

}