package com.skosc.tkffintech.model.repo

import com.google.gson.Gson
import com.skosc.tkffintech.entities.UserCredentials
import com.skosc.tkffintech.entities.UserInfo
import com.skosc.tkffintech.model.dao.SecurityDao
import com.skosc.tkffintech.model.dao.UserInfoDao
import com.skosc.tkffintech.model.room.HomeworkDao
import com.skosc.tkffintech.model.webservice.TinkoffError
import com.skosc.tkffintech.model.webservice.TinkoffUserApi
import com.skosc.tkffintech.utils.own
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class CurrentUserRepoImpl(
        private val userApi: TinkoffUserApi,
        private val securityDao: SecurityDao,
        private val gson: Gson,
        private val userInfoDao: UserInfoDao
) : CurrentUserRepo {

    private val cdisp: CompositeDisposable = CompositeDisposable()

    override val info: Observable<UserInfo> by lazy {
        userInfoDao.rxUserInfo
    }

    override val isLoggedIn: BehaviorSubject<Boolean> = BehaviorSubject.create()

    override val id: Observable<Long> = info.map { it.id }

    init {
        cdisp own securityDao.hasAuthCredentials.subscribe(isLoggedIn::onNext)
    }

    override fun login(email: String, password: String): Single<Boolean> {
        val credentials = UserCredentials(email, password)
        return userApi.signIn(credentials).map {
            if (it.isSuccessful) {
                userInfoDao.saveUserInfo(it.body()
                        ?: throw IllegalStateException("Login succeeded but server does't returned user data"))
                cdisp own securityDao.hasAuthCredentials.subscribe(isLoggedIn::onNext)
                return@map true
            }
            throw RuntimeException(parseError(it.errorBody()?.string() ?: ""))
        }
    }

    override fun signout() {
        securityDao.clearAuthCredentials()
        cdisp own securityDao.hasAuthCredentials.subscribe(isLoggedIn::onNext)
    }

    override fun forceRefreshUserInfo() {
        userApi.getCurrentUserInfo()
                .map { it.body()?.body!! }
                .subscribe { model -> userInfoDao.saveUserInfo(model!!) }
    }

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