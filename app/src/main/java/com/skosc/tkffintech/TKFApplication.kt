package com.skosc.tkffintech

import android.app.Application
import org.kodein.di.Kodein

class TKFApplication : Application() {
    val kodein = Kodein.lazy {
        importOnce(viewModelFactoryModule)
    }

}