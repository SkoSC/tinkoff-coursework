package com.skosc.tkffintech.viewmodel.events

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.model.repo.EventsRepo
import com.skosc.tkffintech.utils.own
import io.reactivex.android.schedulers.AndroidSchedulers

class EventsViewModelImpl(private val eventsRepo: EventsRepo) : EventsViewModel() {
    override val onGoingEvents: MutableLiveData<List<EventInfo>> = MutableLiveData()
    override val archiveEvents: MutableLiveData<List<EventInfo>> = MutableLiveData()

    init {
        eventsRepo.refresh()
        cdisp own eventsRepo.onGoingEvents
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { events ->
                    onGoingEvents.value = events
                }

        cdisp own eventsRepo.archiveEvents
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { events ->
                    archiveEvents.value = events
                }
    }

    override fun update() {
        eventsRepo.tryForceRefresh()
    }
}