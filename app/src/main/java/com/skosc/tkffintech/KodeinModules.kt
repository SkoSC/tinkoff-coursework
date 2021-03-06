package com.skosc.tkffintech

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.skosc.tkffintech.misc.OkHttpLoggingInterceptor
import com.skosc.tkffintech.misc.gsonadapter.JodaDateTimeAdapter
import com.skosc.tkffintech.misc.perscookie.PersistentCookieStore
import com.skosc.tkffintech.model.dao.SecurityDao
import com.skosc.tkffintech.model.dao.SecurityDaoPrefImpl
import com.skosc.tkffintech.model.dao.UserInfoDao
import com.skosc.tkffintech.model.dao.UserInfoDaoImpl
import com.skosc.tkffintech.model.repo.*
import com.skosc.tkffintech.model.room.*
import com.skosc.tkffintech.model.service.GeoSearcher
import com.skosc.tkffintech.model.service.NetworkInfoService
import com.skosc.tkffintech.model.webservice.TinkoffCursesApi
import com.skosc.tkffintech.model.webservice.TinkoffEventsApi
import com.skosc.tkffintech.model.webservice.TinkoffGradesApi
import com.skosc.tkffintech.model.webservice.TinkoffUserApi
import com.skosc.tkffintech.usecase.*
import com.skosc.tkffintech.utils.GlobalConstants
import com.skosc.tkffintech.utils.extensions.DefaultModule
import com.skosc.tkffintech.viewmodel.ViewModelArgs
import com.skosc.tkffintech.viewmodel.coursedetail.CourseDetailViewModel
import com.skosc.tkffintech.viewmodel.coursedetail.CourseDetailViewModelFactory
import com.skosc.tkffintech.viewmodel.courses.CourseViewModel
import com.skosc.tkffintech.viewmodel.courses.CourseViewModelFactory
import com.skosc.tkffintech.viewmodel.eventdetail.EventDetailViewModel
import com.skosc.tkffintech.viewmodel.eventdetail.EventDetailViewModelFactory
import com.skosc.tkffintech.viewmodel.events.ArchiveEventsListViewModel
import com.skosc.tkffintech.viewmodel.events.EventsListViewModelArchiveFactory
import com.skosc.tkffintech.viewmodel.events.EventsListViewModelOngoingFactory
import com.skosc.tkffintech.viewmodel.events.OngoingEventsListViewModel
import com.skosc.tkffintech.viewmodel.grades.GradesManyUserViewModel
import com.skosc.tkffintech.viewmodel.grades.GradesManyUserViewModelFactory
import com.skosc.tkffintech.viewmodel.grades.GradesSingleUserViewModel
import com.skosc.tkffintech.viewmodel.grades.GradesSingleUserViewModelFactory
import com.skosc.tkffintech.viewmodel.login.LoginViewModel
import com.skosc.tkffintech.viewmodel.login.LoginViewModelFactory
import com.skosc.tkffintech.viewmodel.main.MainActivityViewModel
import com.skosc.tkffintech.viewmodel.main.MainActivityViewModelFactory
import com.skosc.tkffintech.viewmodel.profile.ProfileViewModel
import com.skosc.tkffintech.viewmodel.profile.ProfileViewModelFactory
import com.skosc.tkffintech.viewmodel.splash.SplashScreenViewModel
import com.skosc.tkffintech.viewmodel.splash.SplashScreenViewModelFactory
import okhttp3.OkHttpClient
import okhttp3.internal.JavaNetCookieJar
import org.joda.time.DateTime
import org.kodein.di.Kodein
import org.kodein.di.generic.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.CookieStore

private object KodeinTags {
    const val SP_TIMERS = "timers"
    const val SP_USER_INFO = "user-info"
}

fun roomModule(ctx: Context) = Kodein.DefaultModule("room-db") {
    val db: TKFRoomDatabase = Room.databaseBuilder(ctx, TKFRoomDatabase::class.java, GlobalConstants.Room.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    bind<RoomDatabase>() with instance(db)
    bind<EventInfoDao>() with singleton { db.eventInfoDao }
    bind<HomeworkDao>() with singleton { db.homeworkDao }
    bind<GradesDao>() with singleton { db.gradesDao }
    bind<UserDao>() with singleton { db.userDao }
    bind<CourseInfoDao>() with singleton { db.courseInfoDao }
}

fun systemService(ctx: Context) = Kodein.DefaultModule("android-system-service") {
    bind<SharedPreferences>(KodeinTags.SP_TIMERS) with singleton {
        ctx.getSharedPreferences(GlobalConstants.SharedPrefs.Timers.NAME, Context.MODE_PRIVATE)
    }
    bind<SharedPreferences>(KodeinTags.SP_USER_INFO) with singleton {
        ctx.getSharedPreferences(GlobalConstants.SharedPrefs.UserInfo.NAME, Context.MODE_PRIVATE)
    }
    bind<NetworkInfoService>() with singleton { NetworkInfoService(ctx) }
    bind<GeoSearcher>() with singleton { GeoSearcher(ctx) }
}

val daoModule = Kodein.DefaultModule("dao") {
    bind<SecurityDao>() with singleton { SecurityDaoPrefImpl(instance()) }
    bind<UserInfoDao>() with singleton { UserInfoDaoImpl(instance(KodeinTags.SP_USER_INFO), instance()) }
}

val viewModelFactoryModule = Kodein.DefaultModule("view-model") {
    bind<LoginViewModelFactory>(LoginViewModel::class) with provider { LoginViewModelFactory(kodein) }
    bind<MainActivityViewModelFactory>(MainActivityViewModel::class) with provider { MainActivityViewModelFactory(kodein) }
    bind<SplashScreenViewModelFactory>(SplashScreenViewModel::class) with provider { SplashScreenViewModelFactory(kodein) }
    bind<ProfileViewModelFactory>(ProfileViewModel::class) with provider { ProfileViewModelFactory(kodein) }
    bind<EventDetailViewModelFactory>(EventDetailViewModel::class) with factory { args: ViewModelArgs ->
        EventDetailViewModelFactory(kodein, args)
    }
    bind<EventsListViewModelOngoingFactory>(OngoingEventsListViewModel::class) with provider {
        EventsListViewModelOngoingFactory(kodein)
    }
    bind<EventsListViewModelArchiveFactory>(ArchiveEventsListViewModel::class) with provider {
        EventsListViewModelArchiveFactory(kodein)
    }
    bind<CourseDetailViewModelFactory>(CourseDetailViewModel::class) with factory { args: ViewModelArgs ->
        CourseDetailViewModelFactory(kodein, args)
    }
    bind<CourseViewModelFactory>(CourseViewModel::class) with provider {
        CourseViewModelFactory(kodein)
    }
    bind<GradesSingleUserViewModelFactory>(GradesSingleUserViewModel::class) with factory { args: ViewModelArgs ->
        GradesSingleUserViewModelFactory(kodein, args)
    }
    bind<GradesManyUserViewModelFactory>(GradesManyUserViewModel::class) with factory { args: ViewModelArgs ->
        GradesManyUserViewModelFactory(kodein, args)
    }
}

fun webModule(ctx: Context) = Kodein.DefaultModule("web") {
    val gson = GsonBuilder()
            .registerTypeAdapter(DateTime::class.java, JodaDateTimeAdapter())
            .create()

    val cookieStore = PersistentCookieStore(ctx)
    val cookieHandler = CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL)
    val javaNetCookieJar = JavaNetCookieJar(cookieHandler)
    val okhttp = OkHttpClient.Builder()
            .cookieJar(javaNetCookieJar)
            //.addNetworkInterceptor(CookieInterceptor(cookieStore))
            .addNetworkInterceptor(OkHttpLoggingInterceptor())
            .addNetworkInterceptor(StethoInterceptor())
            .build()

    val retrofit = Retrofit.Builder()
            .baseUrl(GlobalConstants.URL.TINKOFF_API_ENDPOINT)
            .client(okhttp)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    bind<CookieStore>() with instance(cookieHandler.cookieStore)
    bind<Gson>() with instance(gson)
    bind<OkHttpClient>() with instance(okhttp)
    bind<TinkoffUserApi>() with singleton { retrofit.create(TinkoffUserApi::class.java) }
    bind<TinkoffEventsApi>() with singleton { retrofit.create(TinkoffEventsApi::class.java) }
    bind<TinkoffCursesApi>() with singleton { retrofit.create(TinkoffCursesApi::class.java) }
    bind<TinkoffGradesApi>() with singleton { retrofit.create(TinkoffGradesApi::class.java) }
}

val repoModule = Kodein.DefaultModule("repo") {
    bind<CurrentUserRepo>() with singleton { CurrentUserRepoImpl(instance(), instance(), instance(), instance()) }
    bind<EventsRepo>() with singleton { EventsRepoImpl(instance(), instance(), instance(KodeinTags.SP_TIMERS), instance()) }
    bind<HomeworkRepo>() with singleton { HomeworkRepoImpl(instance(), instance(), instance(), instance(), instance(), instance(), instance(KodeinTags.SP_TIMERS), instance()) }
    bind<CourseRepo>() with singleton { CourseRepoImplV2(instance(), instance(), instance(), instance(KodeinTags.SP_TIMERS), instance()) }
}

val useCaseModule = Kodein.DefaultModule("user-case") {
    bind<LogoutBomb>() with provider { LogoutBomb(instance(), listOf(instance(KodeinTags.SP_TIMERS), instance(KodeinTags.SP_USER_INFO))) }
    bind<SearchForEvent>() with provider { SearchForEvent(instance()) }
    bind<LoginUser>() with provider { LoginUser(instance(), instance(), instance()) }
    bind<PerformLogout>() with provider { PerformLogout(instance(), instance()) }
    bind<LoadHomeworks>() with provider { LoadHomeworks(instance(), instance()) }
    bind<LoadCourseStatistics>() with provider { LoadCourseStatistics(instance(), instance()) }
    bind<LoadCurrentUserStatistics>() with provider { LoadCurrentUserStatistics(instance(), instance()) }
}