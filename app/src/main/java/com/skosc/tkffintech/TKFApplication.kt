package com.skosc.tkffintech

import android.app.Application
import com.facebook.stetho.Stetho
import org.kodein.di.Kodein

class TKFApplication : Application() {

    /**
     * Instance of kodein's dependency injection container, should be carefully accessed from outside
     */
    val kodein = Kodein.lazy {
        importOnce(systemService(applicationContext))
        importOnce(roomModule(applicationContext))
        importOnce(daoModule)
        importOnce(viewModelFactoryModule)
        importOnce(webModule(applicationContext))
        importOnce(repoModule)
        importOnce(useCaseModule)
    }

    override fun onCreate() {
        super.onCreate()
    }

    private fun initDebug() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}