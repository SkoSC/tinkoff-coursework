package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.model.repo.EventsRepo
import com.skosc.tkffintech.utils.SearchQueryMaker
import io.reactivex.Observable

class SearchForEvent(private val eventsRepo: EventsRepo) {
    fun searchForOngoingEvent(query: String): Observable<List<EventInfo>>
            = eventsRepo.searchEvents(query, true, SearchQueryMaker.Mode.PARTIAL)

    fun searchForArchiveEvent(query: String): Observable<List<EventInfo>>
            = eventsRepo.searchEvents(query, false, SearchQueryMaker.Mode.PARTIAL)
}