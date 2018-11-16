package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.*
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.model.repo.HomeworkRepo
import com.skosc.tkffintech.viewmodel.UserWithGradesSum
import io.reactivex.Observable

class LoadGrades(
        private val homeworkRepo: HomeworkRepo,
        private val currentUserRepo: CurrentUserRepo
) {
    data class TaskWithGrade(val task: HomeworkTask, val grade: HomeworkGrade)

    fun loadGrades(userId: Long, course: String): Observable<List<Pair<Homework, List<TaskWithGrade>>>> {
        return homeworkRepo.homeworks(course).map {
            it.map {
                it to it.tasks.map {
                    TaskWithGrade(it, homeworkRepo.gradesForUserByTask(userId, it.contestId).blockingFirst(
                            HomeworkGrade(0,"0.0", HomeworkStatus.UNKNOWN, User(0, ""), 0)
                    ))
                }
            }
        }
    }

    fun loadGradesForCurrentUser(course: String) : Observable<List<Pair<Homework, List<TaskWithGrade>>>> {
        return currentUserRepo.info
                .firstOrError()
                .flatMapObservable { loadGrades(it.id, course) }
    }

    fun loadGradesSumForAllCourse(course: String) : Observable<List<UserWithGradesSum>> {
        return homeworkRepo.gradesTotalForAllUsersWithCourse(course)
    }

}