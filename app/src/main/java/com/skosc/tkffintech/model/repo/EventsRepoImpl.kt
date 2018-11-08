package com.skosc.tkffintech.model.repo

import android.content.SharedPreferences
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.model.room.EventInfoDao
import com.skosc.tkffintech.model.room.RoomEventInfo
import com.skosc.tkffintech.model.webservice.TinkoffEventsApi
import com.skosc.tkffintech.service.NetworkInfoService
import com.skosc.tkffintech.utils.SQLSearchQueryMaker
import com.skosc.tkffintech.utils.SearchQueryMaker
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTime
import java.lang.IllegalStateException

class EventsRepoImpl(
        private val api: TinkoffEventsApi,
        private val dao: EventInfoDao,
        private val timerSharedPreferences: SharedPreferences,
        private val networknfo: NetworkInfoService
) : EventsRepo {
    private val queryMaker: SearchQueryMaker = SQLSearchQueryMaker()

    private val rxSharedPreferences = RxSharedPreferences.create(timerSharedPreferences)
    private val lastUpdatedPref = rxSharedPreferences.getLong("timer-event-info-update", 0)

    override fun refresh() {
        Single.fromCallable { lastUpdatedPref.get() }
                .filter { lastUpdated -> lastUpdated < DateTime.now().minusHours(1).millis }
                .filter { networknfo.checkConnection() }
                .subscribe {
                    tryForceRefresh()
                }
    }

    override fun tryForceRefresh() {
        api.getAllEvents().subscribe { events ->
            if (!events.isSuccessful) {
                throw IllegalStateException("Network request failed")
            }
            dao.deleteAll()
            val eventBucket = events.body() ?: TinkoffEventsApi.EventBucket()
            val active = eventBucket.active.map { RoomEventInfo.from(it, true) }
            val archive = eventBucket.archive.map { RoomEventInfo.from(it, false) }
            dao.insertAll(active + archive)
            lastUpdatedPref.set(DateTime.now().millis)
        }
    }

    override fun searchEvents(query: String, isOnGoing: Boolean, mode: SearchQueryMaker.Mode): Single<List<EventInfo>> {
        val sqlQuery = queryMaker.from(query, mode)
        return dao.search(sqlQuery, isOnGoing).first(listOf()).map { it.map { it.convert() } }
    }

    override val onGoingEvents: Observable<List<EventInfo>> by lazy {
        dao.getActiveEventInfo().convertToBusinessModel()
    }

    override val archiveEvents: Observable<List<EventInfo>> by lazy {
        dao.getArchiveEventInfo().convertToBusinessModel()
    }
    
    override fun findEventByHid(hid: Long): Single<EventInfo> {
        return dao.searchForEventWithId(hid)
                .map(RoomEventInfo::convert)
                .firstOrError()
    }
    
    private fun Observable<List<RoomEventInfo>>.convertToBusinessModel(): Observable<List<EventInfo>> {
        return map { eventList -> eventList.map { event -> event.convert() } }
    }
}