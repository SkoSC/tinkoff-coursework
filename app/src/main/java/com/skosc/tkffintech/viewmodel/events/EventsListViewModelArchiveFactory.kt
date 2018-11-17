package com.skosc.tkffintech.viewmodel.events

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import org.kodein.di.Kodein

class EventsListViewModelArchiveFactory(kodein: Kodein) : TKFViewModelFactory<EventsListViewModelArchive>(kodein) {
    override fun create(): EventsListViewModelArchive {
        return EventsListViewModelArchive(inject(), inject())
    }
}