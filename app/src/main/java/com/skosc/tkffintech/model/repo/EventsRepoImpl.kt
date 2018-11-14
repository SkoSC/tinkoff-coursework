package com.skosc.tkffintech.model.repo

import android.content.SharedPreferences
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.model.room.EventInfoDao
import com.skosc.tkffintech.model.room.model.RoomEventInfo
import com.skosc.tkffintech.model.webservice.TinkoffEventsApi
import com.skosc.tkffintech.service.NetworkInfoService
import com.skosc.tkffintech.utils.SQLSearchQueryMaker
import com.skosc.tkffintech.utils.SearchQueryMaker
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response

class EventsRepoImpl(
        private val api: TinkoffEventsApi,
        private val dao: EventInfoDao,
        private val timerSharedPreferences: SharedPreferences,
        private val networkInfo: NetworkInfoService
) : EventsRepo {
    private val queryMaker: SearchQueryMaker = SQLSearchQueryMaker()


    override fun tryForceRefresh(): Single<Boolean> {
        return api.getAllEvents()
                .doAfterSuccess { events ->
                    saveEventsToDb(events)
                }.map {
                    it.isSuccessful
                }
    }

    private fun saveEventsToDb(events: Response<TinkoffEventsApi.EventBucket>) {
        if (!events.isSuccessful) {
            throw IllegalStateException("Network request failed")
        }
        dao.deleteAll()
        val eventBucket = events.body() ?: TinkoffEventsApi.EventBucket()
        val active = eventBucket.active.map { RoomEventInfo.from(it, true) }
        val archive = eventBucket.archive.map { RoomEventInfo.from(it, false) }
        dao.insertAll(active + archive)
    }

    override fun searchEvents(query: String, isOnGoing: Boolean, mode: SearchQueryMaker.Mode): Observable<List<EventInfo>> {
        val sqlQuery = queryMaker.from(query, mode)
        return dao.search(sqlQuery, isOnGoing).map { it.map { it.convert() } }
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