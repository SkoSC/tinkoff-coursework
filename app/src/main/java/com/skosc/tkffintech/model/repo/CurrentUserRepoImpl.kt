package com.skosc.tkffintech.model.repo

import android.util.Log
import com.google.gson.Gson
import com.skosc.tkffintech.entities.UserCredentials
import com.skosc.tkffintech.model.webservice.TinkoffError
import com.skosc.tkffintech.model.webservice.TinkoffSignUpApi
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import java.lang.Exception
import java.lang.RuntimeException
import java.nio.charset.Charset

class CurrentUserRepoImpl(private val signUpApi: TinkoffSignUpApi, private val gson: Gson) : CurrentUserRepo {
    override fun login(email: String, password: String): Single<Boolean> {
        val credentials = UserCredentials(email, password)
        return signUpApi.signIn(credentials).map {
            if (it.isSuccessful) {
                isLoggedIn.onNext(true)
                return@map true
            }
            throw RuntimeException(parseError(it.errorBody()?.string() ?: ""))
        }
    }

    override val isLoggedIn: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

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