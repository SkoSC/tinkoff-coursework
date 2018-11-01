package com.skosc.tkffintech.viewmodel.events

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.model.repo.EventsRepo
import com.skosc.tkffintech.utils.observeOnMainThread
import com.skosc.tkffintech.utils.own
import com.skosc.tkffintech.viewmodel.RxViewModel

class EventsViewModelImpl(private val eventsRepo: EventsRepo) : EventsViewModel() {
    override val onGoingEvents: MutableLiveData<List<EventInfo>> = MutableLiveData()
    override val archiveEvents: MutableLiveData<List<EventInfo>> = MutableLiveData()
    init {
        cdisp own eventsRepo.getOnGoingEvents()
                .observeOnMainThread()
                .subscribe({ events ->
                    onGoingEvents.value = events
                }, {
                    Log.e("TKFERR", "", it)
                })

        cdisp own eventsRepo.getArchiveEvents()
                .observeOnMainThread()
                .subscribe({ events ->
                    archiveEvents.value = events
                }, {
                    Log.e("TKFERR", "", it)
                })
    }
}