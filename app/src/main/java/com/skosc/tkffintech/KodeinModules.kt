package com.skosc.tkffintech

import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.model.repo.CurrentUserRepoImpl
import com.skosc.tkffintech.model.webservice.TinkoffSignUpApi
import com.skosc.tkffintech.viewmodel.login.LoginViewModel
import com.skosc.tkffintech.viewmodel.login.LoginViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val viewModelFactoryModule = Kodein.Module("view model", false, "tkf") {
    bind<LoginViewModelFactory>(LoginViewModel::class) with provider { LoginViewModelFactory(kodein) }
}

val retrofitModule = Kodein.Module("retrofit", false, "tkf") {
    val retrofit = Retrofit.Builder()
            .baseUrl("https://fintech.tinkoff.ru")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    bind<TinkoffSignUpApi>() with singleton { retrofit.create(TinkoffSignUpApi::class.java) }
}

val repoModule = Kodein.Module("repo", false, "tkf") {
    bind<CurrentUserRepo>() with provider { CurrentUserRepoImpl(instance()) }
}