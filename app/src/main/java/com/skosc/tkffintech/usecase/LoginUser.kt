package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.misc.UpdateResult
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.utils.extensions.mapEach
import com.skosc.tkffintech.utils.logging.LoggerProvider
import io.reactivex.Single
import java.util.concurrent.TimeUnit

/**
 * Performs user login. Updates data if possible
 */
class LoginUser(private val currentUserRepo: CurrentUserRepo, private val loadCourses: LoadCourses, private val loadHomeworks: LoadHomeworks) {
    companion object {
        /**
         * Arbitrary delay between loading courses and updating homeworks info
         */
        private const val UPDATE_DELAY = 3L
    }

    private val logger = LoggerProvider.get(this)

    /**
     * Leggins user to server.
     *
     * @return Success value of login
     */
    fun login(email: String, password: String): Single<Boolean> {
        logger.info("Trying to login...")
        return currentUserRepo.login(email, password)
                .doOnSuccess { success ->
                    logger.info("Login is successful: $success")
                    if (success) {
                        tryPreload()
                    }
                }
    }

    /**
     * Tries to preload data for user, fails silently
     */
    private fun tryPreload() {
        logger.verbose("Trying to preload data")
        val disp = loadCourses.checkForUpdates()
                .delay(UPDATE_DELAY, TimeUnit.SECONDS) //TODO Dirty hack, remove
                .flatMap {
                    blockingUpdateCourses()
                }.subscribe({
                    val preloaded = it.filter { it == UpdateResult.Updated }.size
                    val total = it.size
                    logger.verbose("Persuading success: $preloaded/$total")
                }, {
                    logger.warning("Preload failed, reason: ${it.message}")
                })
    }

    private fun blockingUpdateCourses() = loadCourses.courses
            .mapEach { course ->
                loadHomeworks.checkForUpdates(course.url).blockingGet()
            }
}