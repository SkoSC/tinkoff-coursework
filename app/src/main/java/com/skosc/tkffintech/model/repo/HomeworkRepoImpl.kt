package com.skosc.tkffintech.model.repo

import android.content.SharedPreferences
import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.misc.DataUpdateResult
import com.skosc.tkffintech.model.entity.ExpirationTimer
import com.skosc.tkffintech.model.room.*
import com.skosc.tkffintech.model.room.model.*
import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import com.skosc.tkffintech.model.webservice.TinkoffGradesApi
import com.skosc.tkffintech.service.NetworkInfoService
import com.skosc.tkffintech.usecase.TaskWithGrade
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

    override fun tryForceUpdate() {
        cdisp own courseInfoDao.allBusiness().subscribe { courseInfos ->
            val a = courseInfos.map { model ->
                val course = model.url
                return@map gradesApi.gradesForCourse(course).subscribe { it -> saveData(course)(it) }
            }
        }

    }

    // TODO Refactor
    fun saveData(course: String) = { resp: List<TinkoffGradesApi.GradesResp> ->
        val nextUpdateTime = DateTime.now().plusSeconds(UPDATE_TIME_POLITIC_SECONDS)
        expTimer.rewind(nextUpdateTime)
        val students = resp.flatMap { it.students }
        val roomUsers = students.map { RoomUser(it.id, it.name) }
        userDao.insertOrUpdate(roomUsers)

        val courseRelation = roomUsers.map { RoomUserCourseRelation(0, it.id, course) }
        userDao.insertCourseUserRelations(courseRelation)

        val grades = resp.flatMap { it.grades() }.map { RoomGrade.from(it) }
        gradesDao.insert(grades)
        cursesApi.homeworks(course).subscribe { homeworksRow ->
            val homeworks = homeworksRow.homeworks.map { it.convert(course) }.map { hw ->
                RoomHomeworkAndTasks(RoomHomework.from(hw), hw.tasks.map { RoomHomeworkTask.from(it, hw.id) })
            }
            homeworks.forEach { homeworkDao.insert(it) }
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
        return homeworkDao.homeWorksForCourse(course).mapEach { homeworkAndTasks ->
            homeworkAndTasks.homework.convert(
                    homeworkAndTasks.tasks.map { task -> task.convert() }
            )
        }
    }

    override fun gradesTotalForAllUsersWithCourse(course: String): Observable<List<UserWithGradesSum>> {
        performSoftUpdate()
        return gradesDao.gradesTotalForUsersWithCourse(course)
                .mapEach { it.convert() }
    }

    override fun gradesSumForUser(userId: Long): Observable<Double> {
        performSoftUpdate()
        return gradesDao.totalScoreOfUser(userId)
    }

    override fun gradesForUser(userId: Long): Observable<List<HomeworkGrade>> {
        performSoftUpdate()
        return gradesDao.allGradesOfUser(userId).mapEach { grade -> grade.convert(User(0, "")) }
    }

    override fun testGradesForUser(userId: Long): Observable<List<HomeworkGrade>> {
        performSoftUpdate()
        return userDao.findById(userId).flatMap {
            val user = it.convert()
            gradesDao.testGradesForUser(user.id).mapEach { grade -> grade.convert(user) }
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

    override fun gradesWithHomework(user: Long, course: String): Observable<List<Pair<Homework, List<TaskWithGrade>>>> {
        return gradesDao.gradesWithHomework(user, course)
                .map { homeworkMappings -> homeworkMappings.groupBy { it.homework } }
                .map { groupedHomework ->
                    groupedHomework.map { grouped ->
                        val tasks = grouped.value.map { it.task.convert() }
                        val homework = grouped.key.convert(tasks)
                        val grades = grouped.value.map { it.grade.convert(User(0, "")) }
                        assert(grades.size == tasks.size)
                        return@map homework to tasks.mapIndexed { index, homeworkTask ->
                            TaskWithGrade(homeworkTask, grades[index])
                        }
                    }
                }
    }
}