package com.skosc.tkffintech.model.repo

import android.util.Log
import com.skosc.tkffintech.entities.UserCredentials
import com.skosc.tkffintech.model.webservice.TinkoffSignUpApi
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import java.nio.charset.Charset

class CurrentUserRepoImpl(private val signUpApi: TinkoffSignUpApi) : CurrentUserRepo {
    override fun login(email: String, password: String) : Single<Boolean> {
        val credentials = UserCredentials(email, password)
        return signUpApi.signIn(credentials).map {
            Log.i("TKF_INFO", it.message())
            Log.i("TKF_INFO", it.body().toString())
            isLoggedIn.onNext(true)
            true
        }
    }

    override val isLoggedIn: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
}