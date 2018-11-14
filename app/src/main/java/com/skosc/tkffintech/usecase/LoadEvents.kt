package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.model.repo.EventsRepo
import io.reactivex.Observable

class LoadEvents(private val eventsRepo: EventsRepo ) {
    init {
        eventsRepo.refresh()
    }

    val ongoingEvents: Observable<List<EventInfo>> get() = eventsRepo.onGoingEvents
    val archiveEvents: Observable<List<EventInfo>> get() = eventsRepo.archiveEvents

    fun tryLoadEventsFromNetwork() {
        eventsRepo.tryForceRefresh()
    }

    fun loadEvent(id: Long) = eventsRepo.findEventByHid(id)
}