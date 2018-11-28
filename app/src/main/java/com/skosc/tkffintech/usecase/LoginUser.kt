package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.utils.extensions.mapEach
import com.skosc.tkffintech.utils.logging.LoggerProvider
import io.reactivex.Single

class LoginUser(private val currentUserRepo: CurrentUserRepo, private val loadCourses: LoadCourses, private val loadHomeworks: LoadHomeworks) {
    private val logger = LoggerProvider.get(this)

    fun login(email: String, password: String): Single<Boolean> {
        return currentUserRepo.login(email, password)
                .doOnSuccess { success ->
                    if (success) {
                        tryPreload()
                    }
                }
    }

    /**
     * Tries to preload data for user, fails silently
     */
    private fun tryPreload() {
        val disp = loadCourses.checkForUpdates().flatMap {
            loadCourses.courses.mapEach { loadHomeworks.checkForUpdates(it.url).blockingGet() }
        }.subscribe({}, {
            logger.warning("Preload failed, reason: ${it.message}")
        })
    }
}