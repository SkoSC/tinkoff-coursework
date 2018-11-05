package com.skosc.tkffintech.viewmodel.events

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.model.repo.EventsRepo
import com.skosc.tkffintech.utils.observeOnMainThread
import com.skosc.tkffintech.utils.own

class EventsViewModelImpl(private val eventsRepo: EventsRepo) : EventsViewModel() {
    override val onGoingEvents: MutableLiveData<List<EventInfo>> = MutableLiveData()
    override val archiveEvents: MutableLiveData<List<EventInfo>> = MutableLiveData()

    init {
        cdisp own eventsRepo.getOnGoingEvents()
                .observeOnMainThread()
                .subscribe({ events ->
                    onGoingEvents.value = events
                }, {

                })

        cdisp own eventsRepo.getArchiveEvents()
                .observeOnMainThread()
                .subscribe({ events ->
                    archiveEvents.value = events
                }, {

                })
    }

    override fun update() {
        eventsRepo.forceRefresh()
        cdisp own eventsRepo.getOnGoingEvents()
                .observeOnMainThread()
                .subscribe({ events ->
                    onGoingEvents.value = events
                }, {

                })

        cdisp own eventsRepo.getArchiveEvents()
                .observeOnMainThread()
                .subscribe({ events ->
                    archiveEvents.value = events
                }, {

                })
    }
}