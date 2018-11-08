package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.utils.SearchQueryMaker
import io.reactivex.Observable
import io.reactivex.Single

interface EventsRepo {
    fun refresh()
    fun tryForceRefresh()
    val onGoingEvents: Observable<List<EventInfo>>
    val archiveEvents: Observable<List<EventInfo>>
    fun findEventByHid(hid: Long): Single<EventInfo>
    fun searchEvents(query: String, isOnGoing: Boolean, mode: SearchQueryMaker.Mode): Single<List<EventInfo>>
}