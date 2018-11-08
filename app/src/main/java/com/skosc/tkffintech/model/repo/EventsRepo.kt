package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.EventInfo
import io.reactivex.Observable
import io.reactivex.Single

interface EventsRepo {
    fun refresh()
    fun tryForceRefresh()
    val onGoingEvents: Observable<List<EventInfo>>
    val archiveEvents: Observable<List<EventInfo>>
    fun findEventByHid(hid: Long): Single<EventInfo>
}