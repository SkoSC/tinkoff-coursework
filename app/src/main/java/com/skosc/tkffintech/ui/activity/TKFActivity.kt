package com.skosc.tkffintech.ui.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.skosc.tkffintech.TKFApplication
import org.kodein.di.direct
import org.kodein.di.generic.instance
import kotlin.reflect.KClass

abstract class TKFActivity : AppCompatActivity() {
    @Suppress("NOTHING_TO_INLINE")
    inline fun <T : ViewModel> getViewModel(cls: KClass<T>): T {
        val tkfApplication = application as TKFApplication
        val factory = tkfApplication.kodein.direct.instance<ViewModelProvider.Factory>(cls)
        return ViewModelProviders.of(this, factory).get(cls.java)
    }
/*
    @Suppress("NOTHING_TO_INLINE")
    inline fun <T : ViewModel> getViewModel(cls: KClass<T>, args: Map<String, String>): T {

    } */
}