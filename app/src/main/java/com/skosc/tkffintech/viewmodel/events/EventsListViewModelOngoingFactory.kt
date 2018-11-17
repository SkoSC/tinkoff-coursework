package com.skosc.tkffintech.viewmodel.events

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import org.kodein.di.Kodein

class EventsListViewModelOngoingFactory(kodein: Kodein) : TKFViewModelFactory<EventsListViewModelOngoing>(kodein) {
    override fun create(): EventsListViewModelOngoing {
        return EventsListViewModelOngoing(inject(), inject())
    }
}