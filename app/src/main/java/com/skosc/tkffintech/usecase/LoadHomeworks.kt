package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.misc.DataUpdateResult
import com.skosc.tkffintech.model.repo.HomeworkRepo
import io.reactivex.Single

class LoadHomeworks(private val homeworkRepo: HomeworkRepo) {
    fun checkForUpdates(course: String) : Single<DataUpdateResult> {
        return homeworkRepo.checkForUpdates(course)
    }

    fun tryLoadHomewroksFromNetwork(course: String): Single<DataUpdateResult> {
        return homeworkRepo.tryForceRefresh(course)
    }
}