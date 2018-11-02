package com.skosc.tkffintech

import android.util.Log
import com.google.gson.GsonBuilder
import com.skosc.tkffintech.misc.gsonadapter.JodaDateTimeAdapter
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.model.repo.CurrentUserRepoImpl
import com.skosc.tkffintech.model.repo.EventsRepo
import com.skosc.tkffintech.model.repo.EventsRepoImpl
import com.skosc.tkffintech.model.webservice.TinkoffEventsApi
import com.skosc.tkffintech.model.webservice.TinkoffSignUpApi
import com.skosc.tkffintech.viewmodel.events.*
import com.skosc.tkffintech.viewmodel.login.LoginViewModel
import com.skosc.tkffintech.viewmodel.login.LoginViewModelFactory
import okhttp3.OkHttpClient
import org.joda.time.DateTime
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.sin
import okhttp3.internal.JavaNetCookieJar
import okhttp3.logging.HttpLoggingInterceptor
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.CookiePolicy.ACCEPT_ALL
import java.net.URI


val viewModelFactoryModule = Kodein.Module("view model", false, "tkf") {
    bind<LoginViewModelFactory>(LoginViewModel::class) with provider { LoginViewModelFactory(kodein) }
    bind<EventsViewModelFactory>(EventsViewModel::class) with provider { EventsViewModelFactory(kodein) }
    bind<MainActivityViewModelFactory>(MainActivityViewModel::class) with provider { MainActivityViewModelFactory(kodein) }
}

val retrofitModule = Kodein.Module("retrofit", false, "tkf") {
    val gson = GsonBuilder()
            .registerTypeAdapter(DateTime::class.java, JodaDateTimeAdapter())
            .create()

    val cookieManager = CookieManager()
    cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
    val javaNetCookieJar = JavaNetCookieJar(cookieManager)

    val okhttp = OkHttpClient.Builder()
            .cookieJar(javaNetCookieJar)
            .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Log.i("TKF_INFO", message)
            })).build()

    cookieManager.cookieStore.removeAll()

    val retrofit = Retrofit.Builder()
            .baseUrl("https://fintech.tinkoff.ru/api/")
            .client(okhttp)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    bind<OkHttpClient>() with instance(okhttp)
    bind<TinkoffSignUpApi>() with singleton { retrofit.create(TinkoffSignUpApi::class.java) }
    bind<TinkoffEventsApi>() with singleton { retrofit.create(TinkoffEventsApi::class.java) }
}

val repoModule = Kodein.Module("repo", false, "tkf") {
    bind<CurrentUserRepo>() with singleton { CurrentUserRepoImpl(instance()) }
    bind<EventsRepo>() with singleton { EventsRepoImpl(instance()) }
}