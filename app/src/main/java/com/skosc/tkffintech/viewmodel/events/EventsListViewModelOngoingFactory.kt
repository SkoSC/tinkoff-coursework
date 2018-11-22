package com.skosc.tkffintech.viewmodel.events

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import org.kodein.di.Kodein

class EventsListViewModelOngoingFactory(kodein: Kodein) : TKFViewModelFactory<GenericEventsListViewModel>(kodein) {
    override fun create(): GenericEventsListViewModel {
        return OngoingEventsListViewModel(inject(), inject())
    }
}