package com.skosc.tkffintech.viewmodel.events

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import org.kodein.di.Kodein

class EventsListViewModelArchiveFactory(kodein: Kodein) : TKFViewModelFactory<GenericEventsListViewModel>(kodein) {
    override fun create(): GenericEventsListViewModel {
        return ArchiveEventsListViewModel(inject(), inject())
    }
}