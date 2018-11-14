package com.skosc.tkffintech.viewmodel.events

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.usecase.LoadEvents
import com.skosc.tkffintech.usecase.SearchForEvent
import com.skosc.tkffintech.utils.own
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

class EventsListViewModelOngoing(private val eventsLoader: LoadEvents, private val eventSearcher: SearchForEvent) : EventsListViewModel() {
    private val eventsSubject = PublishSubject.create<List<EventInfo>>()
    override val events: MutableLiveData<List<EventInfo>> = MutableLiveData()

    init {
        eventsLoader.ongoingEvents.subscribe(eventsSubject)

        cdisp own eventsSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { eventList ->
                    events.value = eventList
                }
    }

    override fun searchEvents(query: String) {
        cdisp own eventSearcher.searchForOngoingEvent(query)
                .subscribe { events ->
                    eventsSubject.onNext(events)
                }
    }

    override fun update() {
        eventsLoader.tryLoadEventsFromNetwork()
    }

}