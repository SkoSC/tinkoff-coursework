package com.skosc.tkffintech.viewmodel.eventdetail

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.GeoAddress
import com.skosc.tkffintech.model.repo.EventsRepo
import com.skosc.tkffintech.model.service.GeoSearcher
import com.skosc.tkffintech.utils.extensions.observeOnMainThread
import com.skosc.tkffintech.utils.extensions.own
import com.skosc.tkffintech.utils.extensions.subscribeOnIoThread

class EventDetailViewModelImpl(
        hid: Long,
        eventsRepo: EventsRepo,
        private val geoSearcher: GeoSearcher
) : EventDetailViewModel() {

    override val title: MutableLiveData<String> = MutableLiveData()
    override val description: MutableLiveData<String> = MutableLiveData()
    override val place: MutableLiveData<String> = MutableLiveData()
    override val eventDates: MutableLiveData<EventDates> = MutableLiveData()
    override val addresses: MutableLiveData<List<GeoAddress>> = MutableLiveData()

    init {
        cdisp own eventsRepo.findEventByHid(hid).observeOnMainThread().subscribe { eventInfo ->
            title.value = eventInfo.title
            description.value = eventInfo.description
            place.value = eventInfo.place
            eventDates.value = EventDates(eventInfo.dateBegin, eventInfo.dateEnd)

            val tokenizePlaces = tokenizePlace(eventInfo.place)
            cdisp own geoSearcher.findAll(tokenizePlaces)
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