package com.skosc.tkffintech.viewmodel.eventdetail

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.entities.GeoAddress
import com.skosc.tkffintech.viewmodel.RxViewModel
import org.joda.time.DateTime

abstract class EventDetailViewModel : RxViewModel() {
    data class EventDates(val from: DateTime, val to: DateTime)
    companion object {
        const val ARG_HID = "hid"
    }


    abstract val title: LiveData<String>
    abstract val description: LiveData<String>
    abstract val place: LiveData<String>
    abstract val eventDates: LiveData<EventDates>
    abstract val addresses: LiveData<List<GeoAddress>>
}