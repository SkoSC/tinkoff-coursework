package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.misc.UpdateResult
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.model.repo.HomeworkRepo
import com.skosc.tkffintech.viewmodel.HomeworkWithGrades
import io.reactivex.Single

class LoadHomeworks(private val userRepo: CurrentUserRepo, private val homeworkRepo: HomeworkRepo) {
    fun gradesForCurrentUser(course: String): Single<List<HomeworkWithGrades>> {
        val id = userRepo.idBlocking
                ?: return Single.error(IllegalStateException("User not logged in"))
        return homeworkRepo.grades(id, course)
    }

    fun checkForUpdates(course: String): Single<UpdateResult> {
        return homeworkRepo.checkForUpdates(course)
    }

    fun tryLoadHomewroksFromNetwork(course: String): Single<UpdateResult> {
        return homeworkRepo.tryForceRefresh(course)
    }
}