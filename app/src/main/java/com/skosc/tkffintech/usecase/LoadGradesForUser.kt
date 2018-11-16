package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.*
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.model.repo.HomeworkRepo
import io.reactivex.Observable

class LoadGradesForUser(private val homeworkRepo: HomeworkRepo, private val currentUserRepo: CurrentUserRepo) {
    data class TaskWithGrade(val task: HomeworkTask, val grade: HomeworkGrade)

    fun load2(userId: Long, course: String): Observable<List<Pair<Homework, List<TaskWithGrade>>>> {
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

    fun loadForCurrentUser2(course: String) : Observable<List<Pair<Homework, List<TaskWithGrade>>>> {
        return currentUserRepo.info
                .firstOrError()
                .flatMapObservable { load2(it.id, course) }
    }

}