package com.skosc.tkffintech

import android.app.Application
import org.kodein.di.Kodein

class TKFApplication : Application() {
    val kodein = Kodein.lazy {
        importOnce(roomModule(applicationContext))
        importOnce(daoModule(applicationContext))
        importOnce(viewModelFactoryModule)
        importOnce(retrofitModule)
        importOnce(repoModule)
    }

}