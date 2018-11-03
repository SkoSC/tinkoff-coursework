package com.skosc.tkffintech.model.dao

import android.content.Context
import com.f2prateek.rx.preferences2.RxSharedPreferences
import io.reactivex.Single

class SecurityDaoPrefImpl(ctx: Context) : SecurityDao {
    private val prefernces = ctx.getSharedPreferences("tkf-security", Context.MODE_PRIVATE)
    private val rxPrefences: RxSharedPreferences = RxSharedPreferences.create(prefernces)

    private val authCookie = rxPrefences.getString("auth-cookie")

    override fun getAuthCookie(): Single<String> = Single.fromCallable { authCookie.get() }

    override fun setAuthCookie(cookie: String) {
        authCookie.set(cookie)
    }
}