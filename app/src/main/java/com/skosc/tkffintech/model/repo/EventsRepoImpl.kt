package com.skosc.tkffintech.model.repo

import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.model.webservice.TinkoffEventsApi
import io.reactivex.Single

class EventsRepoImpl(private val api: TinkoffEventsApi) : EventsRepo {
    override fun getOnGoingEvents() : Single<List<EventInfo>> {
        return api.getAllEvents()
                .map { it.body()?.active ?: listOf() }
    }
}