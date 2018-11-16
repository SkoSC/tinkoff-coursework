package com.skosc.tkffintech.model.dao

import io.reactivex.Single
import java.net.CookieStore
import java.net.URI

class SecurityDaoPrefImpl(private val cookieStore: CookieStore) : SecurityDao {
    private val requiredTokens = listOf("csrftoken", "anygen")

    override val hasAuthCredentials: Single<Boolean>
        get() {
            return Single.fromCallable {
                val s = cookieStore.get(URI.create("https://fintech.tinkoff.ru/"))
                s.map { it.name }
                        .containsAll(requiredTokens)
            }
        }

    override fun clearAuthCredentials() {
        cookieStore.removeAll()
    }
}