package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.EventInfo
import io.reactivex.Single

interface EventsRepo {
    fun getOnGoingEvents(): Single<List<EventInfo>>
    fun getArchiveEvents(): Single<List<EventInfo>>
}