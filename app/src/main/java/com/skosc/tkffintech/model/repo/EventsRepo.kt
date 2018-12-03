package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.misc.SearchQueryMaker
import com.skosc.tkffintech.misc.model.UpdateResult
import io.reactivex.Observable
import io.reactivex.Single

interface EventsRepo {

    /**
     * Loads events info from network
     */
    fun tryForceRefresh(): Single<UpdateResult>

    /**
     * Loads events info from network, with respect to cache policy and network status
     */
    fun softUpdate(): Single<UpdateResult>

    /**
     * All loaded ongoing events
     */
    val onGoingEvents: Observable<List<EventInfo>>

    /**
     * All loaded archive events
     */
    val archiveEvents: Observable<List<EventInfo>>

    /**
     * Searches event by passed [EventInfo.hid]
     */
    fun findEventByHid(hid: Long): Single<EventInfo>

    /**
     * Searches events by passed query and options
     */
    fun searchEvents(query: String, isOnGoing: Boolean, mode: SearchQueryMaker.Mode): Observable<List<EventInfo>>
}