package com.skosc.tkffintech.viewmodel.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.instance

class EventsViewModelFactory(private val kodein: Kodein) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventsViewModelImpl(kodein.direct.instance()) as T
    }
}