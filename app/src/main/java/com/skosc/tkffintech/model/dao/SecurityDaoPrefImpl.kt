package com.skosc.tkffintech.model.dao

import com.skosc.tkffintech.utils.GlobalConstants.COOKIES
import com.skosc.tkffintech.utils.GlobalConstants.URL
import io.reactivex.Single
import java.net.CookieStore
import java.net.URI

class SecurityDaoPrefImpl(private val cookieStore: CookieStore) : SecurityDao {
    private val requiredTokens = listOf(COOKIES.CSRF_TOKEN, COOKIES.ANYGEN)

    override val hasAuthCredentials: Single<Boolean>
        get() {
            return Single.fromCallable {
                val s = cookieStore.get(URI.create(URL.TINKOFF_FINTECH_ROOT))
                s.map { it.name }
                        .containsAll(requiredTokens)
            }
        }

    override fun clearAuthCredentials() {
        cookieStore.removeAll()
    }
}