package com.skosc.tkffintech.model.repo

import com.google.gson.Gson
import com.skosc.tkffintech.entities.UserCredentials
import com.skosc.tkffintech.entities.UserInfo
import com.skosc.tkffintech.model.dao.SecurityDao
import com.skosc.tkffintech.model.webservice.TinkoffError
import com.skosc.tkffintech.model.webservice.TinkoffUserApi
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

class CurrentUserRepoImpl(private val userApi: TinkoffUserApi, private val securityDao: SecurityDao, private val gson: Gson) : CurrentUserRepo {
    override fun login(email: String, password: String): Single<Boolean> {
        val credentials = UserCredentials(email, password)
        return userApi.signIn(credentials).map {
            if (it.isSuccessful) {
                val authCookie = it.headers().values("Set-Cookie")
                        .find { it.contains("anygen") }
                        ?.split(";")
                        ?.first() ?: ""

                securityDao.setAuthCookie(authCookie)

                isLoggedIn.onNext(true)
                return@map true
            }
            throw RuntimeException(parseError(it.errorBody()?.string() ?: ""))
        }
    }

    override val isLoggedIn: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    override val info: Single<UserInfo>
        get() {
            return securityDao.getAuthCookie()
                    .flatMap {
                        userApi.getCurrentUserInfo(it)
                    }.map {
                        it.body()!!.body
                    }
        }

    // TODO Think how to remove dependency on [TinkoffError]
    private fun parseError(body: String): String {
        return try {
            val tinkoffError = gson.fromJson<TinkoffError>(body, TinkoffError::class.java)
            when (tinkoffError.detail) {
                "Username or password is incorrect" -> NetworkErrors.WRONG_CREDENTIALS
                else -> NetworkErrors.UNKNOWN
            }
        } catch (e: Throwable) {
            NetworkErrors.UNKNOWN
        }
    }
}