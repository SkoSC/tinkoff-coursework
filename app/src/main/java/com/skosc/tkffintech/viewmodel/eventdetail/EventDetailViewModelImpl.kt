package com.skosc.tkffintech.viewmodel.eventdetail

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.GeoAddress
import com.skosc.tkffintech.usecase.LoadEvents
import com.skosc.tkffintech.usecase.SearchLocation
import com.skosc.tkffintech.utils.extensions.observeOnMainThread
import com.skosc.tkffintech.utils.extensions.own
import com.skosc.tkffintech.utils.extensions.subscribeOnIoThread

class EventDetailViewModelImpl(
        hid: Long,
        loadEvent: LoadEvents,
        private val searchLocation: SearchLocation
) : EventDetailViewModel() {

    override val title: MutableLiveData<String> = MutableLiveData()
    override val description: MutableLiveData<String> = MutableLiveData()
    override val place: MutableLiveData<String> = MutableLiveData()
    override val eventDates: MutableLiveData<EventDates> = MutableLiveData()
    override val addresses: MutableLiveData<List<GeoAddress>> = MutableLiveData()

    init {
        cdisp own loadEvent.loadEvent(hid).observeOnMainThread().subscribe { eventInfo ->
            title.value = eventInfo.title
            description.value = eventInfo.description
            place.value = eventInfo.place
            eventDates.value = EventDates(eventInfo.dateBegin, eventInfo.dateEnd)

            val tokenizePlaces = tokenizePlace(eventInfo.place)
            cdisp own searchLocation.findAddresses(tokenizePlaces)
                    .subscribeOnIoThread()
                    .observeOnMainThread()
                    .subscribe { resolved ->
                        addresses.value = resolved
                    }
        }
    }

    private fun tokenizePlace(place: String): List<String> {
        return place.split('.', ' ', ',', 'и')
                .filter { it.isNotBlank() }
                .map { it.trim() }
    }
}