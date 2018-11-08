package com.skosc.tkffintech.viewmodel.events

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.model.repo.EventsRepo
import com.skosc.tkffintech.utils.SearchQueryMaker
import com.skosc.tkffintech.utils.own
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

class EventsListViewModelOngoing(private val eventsRepo: EventsRepo) : EventsListViewModel() {
    private val eventsSubject = PublishSubject.create<List<EventInfo>>()
    override val events: MutableLiveData<List<EventInfo>> = MutableLiveData()

    init {
        eventsRepo.refresh()
        eventsRepo.onGoingEvents.subscribe(eventsSubject)

        cdisp own eventsSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { eventList ->
                    events.value = eventList
                }
    }

    override fun searchEvents(query: String) {
        cdisp own eventsRepo.searchEvents(query, true, SearchQueryMaker.Mode.PARTIAL)
                .subscribe { events ->
                    eventsSubject.onNext(events)
                }
    }

    override fun update() {
        eventsRepo.tryForceRefresh()
    }

}