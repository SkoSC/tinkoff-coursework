package com.skosc.tkffintech.viewmodel.eventdetail

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.usecase.LoadEvents
import com.skosc.tkffintech.utils.observeOnMainThread

class EventDetailViewModelImpl(private val loadeEvent: LoadEvents) : EventDetailViewModel() {
    override val title: MutableLiveData<String> = MutableLiveData()
    override val description: MutableLiveData<String> = MutableLiveData()
    override val place: MutableLiveData<String> = MutableLiveData()
    override val eventDates: MutableLiveData<EventDates> = MutableLiveData()

    override fun init(hid: Long) {
        loadeEvent.loadEvent(hid).observeOnMainThread().subscribe { eventInfo ->
            title.value = eventInfo.title
            description.value = eventInfo.description
            place.value = eventInfo.place
            eventDates.value = EventDates(eventInfo.dateBegin, eventInfo.dateEnd)
        }
    }
}