package com.skosc.tkffintech.model.repo

import android.content.SharedPreferences
import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.entities.composite.HomeworkWithGrades
import com.skosc.tkffintech.entities.composite.UserWithGradesSum
import com.skosc.tkffintech.misc.model.UpdateResult
import com.skosc.tkffintech.model.entity.ExpirationTimer
import com.skosc.tkffintech.model.room.CourseInfoDao
import com.skosc.tkffintech.model.room.GradesDao
import com.skosc.tkffintech.model.room.HomeworkDao
import com.skosc.tkffintech.model.room.UserDao
import com.skosc.tkffintech.model.room.model.*
import com.skosc.tkffintech.model.service.NetworkInfoService
import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import com.skosc.tkffintech.model.webservice.TinkoffGradesApi
import com.skosc.tkffintech.model.webservice.model.GradesResp
import com.skosc.tkffintech.model.webservice.model.HomeworksResp
import com.skosc.tkffintech.utils.extensions.extractUpdateResult
import com.skosc.tkffintech.utils.extensions.mapEach
import com.skosc.tkffintech.utils.extensions.own
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Response
import java.util.concurrent.TimeUnit

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
        private const val UPDATE_TIME_POLITIC_SECONDS: Long = 60 * 60
    }

    private val cdisp = CompositeDisposable()
    private val dataRefresh = HashMap<String, ExpirationTimer>()

    override fun grades(user: Long, course: String): Single<List<HomeworkWithGrades>> {
        return gradesDao.gradesWithHomework(user, course)
                .map(this::tasksToBindedGrades)
    }

    override fun grades(user: Long): Single<List<HomeworkWithGrades>> {
        return gradesDao.gradesWithHomework(user).map {
            tasksToBindedGrades(it)
        }
    }

    override fun coursesCount(user: Long): Single<Int> {
        return courseInfoDao.count()
    }

    override fun totalScore(user: Long): Single<Double> {
        return gradesDao.totalScoreOfUser(user)
    }

    override fun totalTests(user: Long): Single<Int> {
        return gradesDao.testGradesForUser(user).map { it.size }
    }

    override fun gradesSum(course: String): Single<List<UserWithGradesSum>> {
        return gradesDao.gradesTotalForUsersWithCourse(course).mapEach(RoomUserWithGradesSum::convert)
    }

    private fun tasksToBindedGrades(roomData: List<RoomHomworkToTasks>): List<HomeworkWithGrades> {
        val groupedByHomework = roomData.groupBy { it.homework }
        return groupedByHomework.entries.map { (roomHomework, value) ->
            val taskToGrade = value.map { Pair(it.task.convert(), it.grade.convert(User(0, ""))) }
            val homework = roomHomework.convert(taskToGrade.map { it.first })
            return@map HomeworkWithGrades(homework, taskToGrade)
        }
    }

    override fun tryForceRefresh(course: String): Single<UpdateResult> {
        return gradesApi.gradesForCourse(course)
                .delay(1, TimeUnit.SECONDS)
                .map { it ->
                    saveData(course, it)
                    it
                }
                .delay(1, TimeUnit.SECONDS)
                .map(Response<*>::extractUpdateResult)
    }

    private fun saveData(course: String, resp: Response<List<GradesResp>>) {
        dataRefresh[course]!!.rewindForward(UPDATE_TIME_POLITIC_SECONDS, TimeUnit.SECONDS)

        val rowGrades = resp.body()!!
        val students = rowGrades.flatMap { it.students }
        val roomUsers = students.map { RoomUser(it.id, it.name) }

        saveUsers(roomUsers)
        saveCourseRealtions(roomUsers, course)
        saveGrades(rowGrades)
        loadAndSaveHomeworks(course)
    }

    private fun saveUsers(roomUsers: List<RoomUser>) {
        userDao.insertOrUpdate(roomUsers)
    }

    private fun loadAndSaveHomeworks(course: String) {
        cdisp own cursesApi.homeworks(course).subscribe { homeworksRow ->
            saveHomeworks(homeworksRow, course)
        }
    }

    private fun saveHomeworks(homeworksRow: HomeworksResp, course: String) {
        val homeworks = homeworksRow.homeworks.map { it.convert(course) }.map { hw ->
            RoomHomeworkAndTasks(RoomHomework.from(hw), hw.tasks.map { RoomHomeworkTask.from(it, hw.id) })
        }
        homeworks.forEach { homeworkDao.insert(it) }
    }

    private fun saveGrades(rowGrades: List<GradesResp>) {
        val grades = rowGrades.flatMap { it.grades() }.map { RoomGrade.from(it) }
        gradesDao.insert(grades)
    }

    private fun saveCourseRealtions(roomUsers: List<RoomUser>, course: String) {
        val courseRelation = roomUsers.map { RoomUserCourseRelation(0, it.id, course) }
        userDao.insertCourseUserRelations(courseRelation)
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
            return dataRefresh[course]!!.isExpired.map { it && networkInfo.checkConnection() }
        }

        return makeExpirationTimer(course).isExpired.map { it && networkInfo.checkConnection() }
    }

    private fun makeExpirationTimer(course: String): ExpirationTimer {
        val key = makeRefreshKey(course)
        val expirationTimer = ExpirationTimer.create(timersSharedPreferences, key)
        dataRefresh[course] = expirationTimer
        return expirationTimer
    }

    private fun makeRefreshKey(course: String): String {
        return "homework-refresh-($course)"
    }
}