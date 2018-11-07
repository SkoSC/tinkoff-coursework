package com.skosc.tkffintech

import android.app.Application
import org.kodein.di.Kodein

class TKFApplication : Application() {

    /**
     * Instance of kodein's dependency injection container, should be carefully accessed from outside
     */
    val kodein = Kodein.lazy {
        importOnce(roomModule(applicationContext))
        importOnce(daoModule(applicationContext))
        importOnce(viewModelFactoryModule)
        importOnce(webModule(applicationContext))
        importOnce(repoModule)
    }

}