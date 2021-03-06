package com.skosc.tkffintech.viewmodel.events

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.misc.Trigger
import com.skosc.tkffintech.misc.model.UpdateResult
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class EventsListViewModel : RxViewModel() {
    abstract val events: LiveData<List<EventInfo>>
    abstract val cardExpanded: LiveData<Boolean>
    abstract val hideSearcheView: Trigger
    abstract fun searchEvents(query: String)
    abstract fun forceUpdate(): LiveData<UpdateResult>
    abstract fun checkForUpdates(): LiveData<UpdateResult>

    abstract fun collapseCard()
    abstract fun expandCard()
}