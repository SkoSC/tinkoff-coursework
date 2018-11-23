package com.skosc.tkffintech.model.repo

import android.content.SharedPreferences
import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.misc.UpdateResult
import com.skosc.tkffintech.model.entity.ExpirationTimer
import com.skosc.tkffintech.model.room.CourseInfoDao
import com.skosc.tkffintech.model.room.GradesDao
import com.skosc.tkffintech.model.room.HomeworkDao
import com.skosc.tkffintech.model.room.UserDao
import com.skosc.tkffintech.model.room.model.*
import com.skosc.tkffintech.model.service.NetworkInfoService
import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import com.skosc.tkffintech.model.webservice.TinkoffGradesApi
import com.skosc.tkffintech.utils.extractUpdateResult
import com.skosc.tkffintech.utils.own
import com.skosc.tkffintech.viewmodel.HomeworkWithGrades
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.joda.time.DateTime
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

    override fun grades(user: Long, course: String): Single<List<HomeworkWithGrades>> {
        return gradesDao.gradesWithHomework(user, course).map {
            transform(it)
        }
    }

    private fun transform(roomData: List<RoomHomworkToTasks>): List<HomeworkWithGrades> {
        val groupedByHomework = roomData.groupBy { it.homework }
        return groupedByHomework.entries.map { (roomHomework, value) ->
            val taskToGrade = value.map { Pair(it.task.convert(), it.grade.convert(User(0, ""))) }
            val homework = roomHomework.convert(taskToGrade.map { it.first })
            return@map HomeworkWithGrades(homework, taskToGrade)
        }
    }

    override fun tryForceRefresh(course: String): Single<UpdateResult> {
        return gradesApi.gradesForCourse(course)
                .map { it ->
                    saveData(course, it)
                    it
                }
                .map(Response<*>::extractUpdateResult)
    }

    // TODO Refactor
    private fun saveData(course: String, resp: Response<List<TinkoffGradesApi.GradesResp>>) {
        val nextUpdateTime = DateTime.now().plusSeconds(UPDATE_TIME_POLITIC_SECONDS)
        dataRefresh[course]!!.rewind(nextUpdateTime)

        val rowGrades = resp.body()!!
        val students = rowGrades.flatMap { it.students }
        val roomUsers = students.map { RoomUser(it.id, it.name) }
        userDao.insertOrUpdate(roomUsers)

        val courseRelation = roomUsers.map { RoomUserCourseRelation(0, it.id, course) }
        userDao.insertCourseUserRelations(courseRelation)

        val grades = rowGrades.flatMap { it.grades() }.map { RoomGrade.from(it) }
        gradesDao.insert(grades)
        cdisp own cursesApi.homeworks(course).subscribe { homeworksRow ->
            val homeworks = homeworksRow.homeworks.map { it.convert(course) }.map { hw ->
                RoomHomeworkAndTasks(RoomHomework.from(hw), hw.tasks.map { RoomHomeworkTask.from(it, hw.id) })
            }
            homeworks.forEach { homeworkDao.insert(it) }
        }
    }

    override fun checkForUpdates(course: String): Single<UpdateResult> {
        return needsUpdate(course).flatMap {
            if (it) {
                tryForceRefresh(course)
            } else {
                Single.just(UpdateResult.NotUpdated)
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
        return "homework-refresh-($course)"
    }
}