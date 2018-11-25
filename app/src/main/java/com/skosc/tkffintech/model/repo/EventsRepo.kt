package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.misc.SearchQueryMaker
import com.skosc.tkffintech.misc.UpdateResult
import io.reactivex.Observable
import io.reactivex.Single

interface EventsRepo {
    fun tryForceRefresh(): Single<UpdateResult>
    fun softUpdate(): Single<UpdateResult>

    val onGoingEvents: Observable<List<EventInfo>>
    val archiveEvents: Observable<List<EventInfo>>
    fun findEventByHid(hid: Long): Single<EventInfo>
    fun searchEvents(query: String, isOnGoing: Boolean, mode: SearchQueryMaker.Mode): Observable<List<EventInfo>>
}