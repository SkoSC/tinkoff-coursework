package com.skosc.tkffintech

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.skosc.tkffintech.misc.gsonadapter.JodaDateTimeAdapter
import com.skosc.tkffintech.model.dao.SecurityDao
import com.skosc.tkffintech.model.dao.SecurityDaoPrefImpl
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.model.repo.CurrentUserRepoImpl
import com.skosc.tkffintech.model.repo.EventsRepo
import com.skosc.tkffintech.model.repo.EventsRepoImpl
import com.skosc.tkffintech.model.room.EventInfoDao
import com.skosc.tkffintech.model.room.TKFRoomDatabase
import com.skosc.tkffintech.model.webservice.TinkoffEventsApi
import com.skosc.tkffintech.model.webservice.TinkoffUserApi
import com.skosc.tkffintech.utils.OkHttpLoggingInterceptor
import com.skosc.tkffintech.viewmodel.events.*
import com.skosc.tkffintech.viewmodel.login.LoginViewModel
import com.skosc.tkffintech.viewmodel.login.LoginViewModelFactory
import com.skosc.tkffintech.viewmodel.profile.ProfileViewModel
import com.skosc.tkffintech.viewmodel.profile.ProfileViewModelFactory
import com.skosc.tkffintech.viewmodel.profile.ProfileViewModelImpl
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
import okhttp3.internal.JavaNetCookieJar
import java.net.CookieManager
import java.net.CookiePolicy

fun roomModule(ctx: Context) = Kodein.Module("room-db", false, "tkf") {
    val db: TKFRoomDatabase = Room.databaseBuilder(ctx, TKFRoomDatabase::class.java, "tkf-default-db")
            .fallbackToDestructiveMigration()
            .build()

    bind<EventInfoDao>() with singleton { db.eventInfoDao }
}

fun daoModule(ctx: Context) = Kodein.Module("dao", false, "tkf") {
    bind<SecurityDao>() with singleton { SecurityDaoPrefImpl(ctx) }
    bind<SharedPreferences>("timers") with singleton { ctx.getSharedPreferences("tkf-timers", Context.MODE_PRIVATE) }
}

val viewModelFactoryModule = Kodein.Module("view-model", false, "tkf") {
    bind<LoginViewModelFactory>(LoginViewModel::class) with provider { LoginViewModelFactory(kodein) }
    bind<EventsViewModelFactory>(EventsViewModel::class) with provider { EventsViewModelFactory(kodein) }
    bind<MainActivityViewModelFactory>(MainActivityViewModel::class) with provider { MainActivityViewModelFactory(kodein) }
    bind<ProfileViewModelFactory>(ProfileViewModel::class) with provider { ProfileViewModelFactory(kodein) }
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
            .addInterceptor(OkHttpLoggingInterceptor("OkHttp"))
            .build()

    val retrofit = Retrofit.Builder()
            .baseUrl("https://fintech.tinkoff.ru/api/")
            .client(okhttp)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    bind<Gson>() with instance(gson)
    bind<OkHttpClient>() with instance(okhttp)
    bind<TinkoffUserApi>() with singleton { retrofit.create(TinkoffUserApi::class.java) }
    bind<TinkoffEventsApi>() with singleton { retrofit.create(TinkoffEventsApi::class.java) }
}

val repoModule = Kodein.Module("repo", false, "tkf") {
    bind<CurrentUserRepo>() with singleton { CurrentUserRepoImpl(instance(), instance(), instance()) }
    bind<EventsRepo>() with singleton { EventsRepoImpl(instance(), instance(), instance("timers")) }
}