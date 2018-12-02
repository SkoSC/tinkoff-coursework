package com.skosc.tkffintech.usecase

import android.content.SharedPreferences
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.misc.model.UpdateResult
import com.skosc.tkffintech.model.repo.EventsRepo
import io.reactivex.Observable
import io.reactivex.Single

class LoadEvents(private val eventsRepo: EventsRepo, sp: SharedPreferences) {
    companion object

    val ongoingEvents: Observable<List<EventInfo>> get() = eventsRepo.onGoingEvents
    val archiveEvents: Observable<List<EventInfo>> get() = eventsRepo.archiveEvents

    fun loadEvent(id: Long) = eventsRepo.findEventByHid(id)

    fun checkForUpdates(): Single<UpdateResult> {
        return eventsRepo.softUpdate()
    }

    fun tryLoadEventsFromNetwork(): Single<UpdateResult> {
        return eventsRepo.tryForceRefresh()
    }
}