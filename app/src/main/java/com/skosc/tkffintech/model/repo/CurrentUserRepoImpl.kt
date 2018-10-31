package com.skosc.tkffintech.model.repo

import android.util.Log
import com.skosc.tkffintech.entities.UserCredentials
import com.skosc.tkffintech.model.webservice.TinkoffSignUpApi
import io.reactivex.Single

class CurrentUserRepoImpl(private val signUpApi: TinkoffSignUpApi) : CurrentUserRepo {
    override fun login(email: String, password: String) : Single<Boolean> {
        val credentials = UserCredentials(email, password)
        return Single.just(0).map {
            true
        }
    }
}