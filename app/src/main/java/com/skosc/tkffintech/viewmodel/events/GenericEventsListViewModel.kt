package com.skosc.tkffintech.viewmodel.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.misc.Trigger
import com.skosc.tkffintech.misc.model.UpdateResult
import com.skosc.tkffintech.utils.extensions.own
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

abstract class GenericEventsListViewModel : EventsListViewModel() {
    protected val eventsSubject = PublishSubject.create<List<EventInfo>>()

    final override val events: MutableLiveData<List<EventInfo>> = MutableLiveData()
    final override val cardExpanded: MutableLiveData<Boolean> = MutableLiveData()
    final override val hideSearcheView: Trigger = Trigger()

    protected abstract val allEvents: Observable<List<EventInfo>>
    protected abstract fun search(query: String): Observable<List<EventInfo>>
    protected abstract fun checkForUpdatesImpl(): Single<UpdateResult>
    protected abstract fun forceUpdateImpl(): Single<UpdateResult>

    init {
        cardExpanded.value = false
        cdisp own eventsSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ eventList ->
                    events.value = eventList
                }, {})
    }

    final override fun searchEvents(query: String) {
        cdisp own search(query)
                .observeOn(AndroidSchedulers.mainThread())
                .first(listOf())
                .subscribe { foundEvents ->
                    events.value = foundEvents
                }
    }

    final override fun forceUpdate(): LiveData<UpdateResult> {
        hideSearcheView.fire()
        val indicator = MutableLiveData<UpdateResult>()
        cdisp own forceUpdateImpl()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ success ->
                    indicator.value = success
                }, {
                    indicator.value = UpdateResult.Error
                })

        return indicator
    }

    final override fun checkForUpdates(): LiveData<UpdateResult> {
        val indicator = MutableLiveData<UpdateResult>()
        cdisp own checkForUpdatesImpl()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ success ->
                    indicator.value = success
                }, {
                    indicator.value = UpdateResult.Error
                })

        return indicator
    }

    final override fun collapseCard() {
        cardExpanded.value = false
    }

    final override fun expandCard() {
        cardExpanded.value = true
    }
}