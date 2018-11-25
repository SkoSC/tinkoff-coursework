package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.composite.HomeworkWithGrades
import com.skosc.tkffintech.misc.UpdateResult
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.model.repo.HomeworkRepo
import io.reactivex.Single

class LoadHomeworks(private val currentUserRepo: CurrentUserRepo, private val homeworkRepo: HomeworkRepo) {
    fun gradesForCurrentUser(course: String): Single<List<HomeworkWithGrades>> {
        val id = currentUserRepo.idBlocking
                ?: return Single.error(IllegalStateException("User not logged in"))
        return gradesForUser(id, course)
    }

    fun gradesForUser(user: Long, course: String): Single<List<HomeworkWithGrades>> {
        return homeworkRepo.grades(user, course)
    }

    fun checkForUpdates(course: String): Single<UpdateResult> {
        return homeworkRepo.checkForUpdates(course)
    }

    fun tryLoadHomewroksFromNetwork(course: String): Single<UpdateResult> {
        return homeworkRepo.tryForceRefresh(course)
    }
}