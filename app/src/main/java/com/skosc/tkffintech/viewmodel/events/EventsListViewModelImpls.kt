package com.skosc.tkffintech.viewmodel.events

import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.misc.model.UpdateResult
import com.skosc.tkffintech.model.repo.EventsRepo
import com.skosc.tkffintech.usecase.SearchForEvent
import io.reactivex.Observable
import io.reactivex.Single

class OngoingEventsListViewModel(
        private val eventsRepo: EventsRepo,
        private val eventSearcher: SearchForEvent
) : GenericEventsListViewModel() {
    override val allEvents: Observable<List<EventInfo>> get() = eventsRepo.onGoingEvents
    override fun search(query: String): Observable<List<EventInfo>> = eventSearcher.searchForOngoingEvent(query)
    override fun checkForUpdatesImpl(): Single<UpdateResult> = eventsRepo.softUpdate()
    override fun forceUpdateImpl(): Single<UpdateResult> = eventsRepo.tryForceRefresh()

    init {
        allEvents.subscribe(eventsSubject)
    }
}

class ArchiveEventsListViewModel(
        private val eventsRepo: EventsRepo,
        private val eventSearcher: SearchForEvent
) : GenericEventsListViewModel() {
    override val allEvents: Observable<List<EventInfo>> get() = eventsRepo.archiveEvents
    override fun search(query: String): Observable<List<EventInfo>> = eventSearcher.searchForArchiveEvent(query)
    override fun checkForUpdatesImpl(): Single<UpdateResult> = eventsRepo.softUpdate()
    override fun forceUpdateImpl(): Single<UpdateResult> = eventsRepo.tryForceRefresh()

    init {
        allEvents.subscribe(eventsSubject)
    }
}
