package com.skosc.tkffintech.usecase

import android.content.SharedPreferences
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.model.entity.ExpirationTimer
import com.skosc.tkffintech.model.repo.EventsRepo
import io.reactivex.Observable
import io.reactivex.Single

class LoadEvents(private val eventsRepo: EventsRepo, sp: SharedPreferences) {
    companion object {
        const val UPDATE_TIME_MILLIS = 60 * 60
    }

    val updateTimer = ExpirationTimer.create(sp, "events-needs-to-forceUpdate")

    init {
        updateTimer.isExpired.filter { it }.subscribe { needsUpdate ->
            eventsRepo.tryForceRefresh()
        }
    }

    val ongoingEvents: Observable<List<EventInfo>> get() = eventsRepo.onGoingEvents
    val archiveEvents: Observable<List<EventInfo>> get() = eventsRepo.archiveEvents

    fun tryLoadEventsFromNetwork(): Single<Boolean> {
        return eventsRepo.tryForceRefresh()
    }

    fun loadEvent(id: Long) = eventsRepo.findEventByHid(id)

    fun checkForUpdates() {
        eventsRepo.performSoftUpdate()
    }
}