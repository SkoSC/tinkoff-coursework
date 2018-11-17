package com.skosc.tkffintech.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.instance

typealias ViewModelArgs = Map<String, String>

abstract class TKFViewModelFactory<T : ViewModel>(protected val kodein: Kodein) : ViewModelProvider.Factory {
    abstract fun create(): T

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return create() as T
    }

    protected inline fun <reified T : Any> inject(): T = kodein.direct.instance()
}

abstract class TKFViewModelFactoryWithArgs<T : ViewModel>(
        protected val kodein: Kodein,
        arguments: ViewModelArgs
) : ViewModelProvider.Factory {
    protected inline fun <reified T : Any> inject(): T = kodein.direct.instance()
}