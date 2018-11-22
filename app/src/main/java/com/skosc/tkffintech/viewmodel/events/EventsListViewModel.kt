package com.skosc.tkffintech.viewmodel.events

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.misc.DataUpdateResult
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class EventsListViewModel : RxViewModel() {
    abstract val events: LiveData<List<EventInfo>>
    abstract val cardExpanded: LiveData<Boolean>
    abstract fun searchEvents(query: String)
    abstract fun forceUpdate(): LiveData<DataUpdateResult>
    abstract fun checkForUpdates(): LiveData<DataUpdateResult>

    abstract fun collapseCard()
    abstract fun expandCard()
}