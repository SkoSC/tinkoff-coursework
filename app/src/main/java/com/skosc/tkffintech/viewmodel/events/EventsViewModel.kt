package com.skosc.tkffintech.viewmodel.events

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class EventsViewModel : RxViewModel() {
    abstract val onGoingEvents: LiveData<List<EventInfo>>
}