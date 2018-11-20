package com.skosc.tkffintech.viewmodel.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.usecase.LoadEvents
import com.skosc.tkffintech.usecase.SearchForEvent
import com.skosc.tkffintech.utils.own
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

class EventsListViewModelArchive(private val eventsLoader: LoadEvents, private val eventSearcher: SearchForEvent) : EventsListViewModel() {
    private val eventsSubject = PublishSubject.create<List<EventInfo>>()

    override val events: MutableLiveData<List<EventInfo>> = MutableLiveData()
    override val cardExpanded: MutableLiveData<Boolean> = MutableLiveData()

    init {
        cardExpanded.value = false

        eventsLoader.archiveEvents.subscribe(eventsSubject)

        cdisp own eventsSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { eventList ->
                    events.value = eventList
                }
    }

    override fun searchEvents(query: String) {
        cdisp own eventSearcher.searchForArchiveEvent(query)
                .subscribe { events ->
                    eventsSubject.onNext(events)
                }
    }

    override fun forceUpdate() {
        cdisp own eventsLoader.tryLoadEventsFromNetwork().subscribe { success ->

        }
    }

    override fun checkForUpdates() {
        eventsLoader.checkForUpdates()
    }

    override fun collapseCard() {
        cardExpanded.value = false
    }

    override fun expandCard() {
        cardExpanded.value = true
    }
}