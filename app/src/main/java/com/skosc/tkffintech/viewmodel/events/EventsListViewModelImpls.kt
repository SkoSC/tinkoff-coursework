package com.skosc.tkffintech.viewmodel.events

import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.misc.UpdateResult
import com.skosc.tkffintech.usecase.LoadEvents
import com.skosc.tkffintech.usecase.SearchForEvent
import io.reactivex.Observable
import io.reactivex.Single

class OngoingEventsListViewModel(
        private val eventsLoader: LoadEvents,
        private val eventSearcher: SearchForEvent
) : GenericEventsListViewModel() {
    override val allEvents: Observable<List<EventInfo>> get() = eventsLoader.ongoingEvents
    override fun search(query: String): Observable<List<EventInfo>> = eventSearcher.searchForOngoingEvent(query)
    override fun checkForUpdatesImpl(): Single<UpdateResult> = eventsLoader.checkForUpdates()
    override fun forceUpdateImpl(): Single<UpdateResult> = eventsLoader.tryLoadEventsFromNetwork()

    init {
        allEvents.subscribe(eventsSubject)
    }
}

class ArchiveEventsListViewModel(
        private val eventsLoader: LoadEvents,
        private val eventSearcher: SearchForEvent
) : GenericEventsListViewModel() {
    override val allEvents: Observable<List<EventInfo>> get() = eventsLoader.archiveEvents
    override fun search(query: String): Observable<List<EventInfo>> = eventSearcher.searchForArchiveEvent(query)
    override fun checkForUpdatesImpl(): Single<UpdateResult> = eventsLoader.checkForUpdates()
    override fun forceUpdateImpl(): Single<UpdateResult> = eventsLoader.tryLoadEventsFromNetwork()

    init {
        allEvents.subscribe(eventsSubject)
    }
}
