package com.skosc.tkffintech.model.repo

import android.content.SharedPreferences
import android.net.NetworkInfo
import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.misc.DataUpdateResult
import com.skosc.tkffintech.model.entity.ExpirationTimer
import com.skosc.tkffintech.model.room.*
import com.skosc.tkffintech.model.room.model.*
import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import com.skosc.tkffintech.model.webservice.TinkoffGradesApi
import com.skosc.tkffintech.service.NetworkInfoService
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
        private val timersSharedPreferences: SharedPreferences,
        private val networkInfo: NetworkInfoService
) : HomeworkRepo {
    companion object {
        private const val UPDATE_TIME_POLITIC_SECONDS = 60 * 60
    }

    private val cdisp = CompositeDisposable()
    private val expTimer = ExpirationTimer.create(timersSharedPreferences, "homework-update")

    override fun tryForceUpdate() {
        cdisp own courseInfoDao.allBusiness().subscribe {
            it.map { model ->
                val course = model.url
                gradesApi.gradesForCourse(course).subscribe { resp ->
                    val nextUpdateTime = DateTime.now().plusSeconds(UPDATE_TIME_POLITIC_SECONDS)
                    expTimer.rewind(nextUpdateTime)
                    val students = resp.flatMap { it.students }
                    val roomUsers = students.map { RoomUser(it.id, it.name) }
                    userDao.insertOrUpdate(roomUsers)

                    val courseRelation = roomUsers.map { RoomUserCourseRelation(0, it.id, course) }
                    userDao.insertCourseUserRelations(courseRelation)

                    val grades = resp.flatMap { it.grades() }.map { RoomGrade.from(it) }
                    gradesDao.insert(grades)
                    cursesApi.homeworks(course).subscribe { homeworks ->
                        val homeworks = homeworks.homeworks.map { it.convert(course) }.map { hw ->
                            RoomHomeworkAndTasks(RoomHomework.from(hw), hw.tasks.map { RoomHomeworkTask.from(it, hw.id) })
                        }
                        homeworks.forEach { homeworkDao.insert(it) }
                    }
                }
            }
        }

    }


    override fun gradesForUserByTask(userId: Long, task: Long): Observable<HomeworkGrade> {
        performSoftUpdate()
        return userDao.findById(userId).flatMap {
            val user = it.convert()
            gradesDao.gradesForUserByTask(userId, task).map { it.convert(user) }
        }
    }


    override fun countAllCourses(): Observable<Int> {
        performSoftUpdate()
        return courseInfoDao.count()
    }

    override fun homeworks(course: String): Observable<List<Homework>> {
        performSoftUpdate()
        return homeworkDao.homeWorksForCourse(course).mapEach { it -> it.homework.convert(it.tasks.map { it.convert() }) }
    }

    override fun gradesTotalForAllUsersWithCourse(course: String): Observable<List<UserWithGradesSum>> {
        performSoftUpdate()
        return gradesDao.gradesTotalForUsersWithCourse(course).map { it.map { it.convert() } }
    }

    override fun gradesSumForUser(user: Long): Observable<Double> {
        performSoftUpdate()
        return gradesDao.totalScoreOfUser(user)
    }

    override fun gradesForUser(user: Long): Observable<List<HomeworkGrade>> {
        performSoftUpdate()
        return userDao.findById(user).flatMap {
            val user = it.convert()
            gradesDao.allGradesOfUser(user.id).map { it.map { it.convert(user) } }
        }
    }

    override fun testGradesForUser(user: Long): Observable<List<HomeworkGrade>> {
        performSoftUpdate()
        return userDao.findById(user).flatMap {
            val user = it.convert()
            gradesDao.testGradesForUser(user.id).map { it.map { it.convert(user) } }
        }
    }

    override fun performSoftUpdate() {
        cdisp own expTimer.isExpired.map { it && networkInfo.checkConnection() }.subscribe { needsUpdate ->
            if (needsUpdate) {
                tryForceUpdate()
                Single.just(DataUpdateResult.Updated)
            } else {
                Single.just(DataUpdateResult.NotUpdated)
            }
        }
    }
}