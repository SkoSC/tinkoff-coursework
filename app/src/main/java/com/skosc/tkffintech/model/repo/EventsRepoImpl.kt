package com.skosc.tkffintech.model.repo

import android.content.SharedPreferences
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.misc.SQLSearchQueryMaker
import com.skosc.tkffintech.misc.SearchQueryMaker
import com.skosc.tkffintech.misc.model.UpdateResult
import com.skosc.tkffintech.model.entity.ExpirationTimer
import com.skosc.tkffintech.model.room.EventInfoDao
import com.skosc.tkffintech.model.room.model.RoomEventInfo
import com.skosc.tkffintech.model.service.NetworkInfoService
import com.skosc.tkffintech.model.webservice.model.EventBucketResp
import com.skosc.tkffintech.model.webservice.TinkoffEventsApi
import com.skosc.tkffintech.utils.extensions.extractUpdateResult
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTime
import retrofit2.Response

class EventsRepoImpl(
        private val api: TinkoffEventsApi,
        private val dao: EventInfoDao,
        timerSharedPreferences: SharedPreferences,
        private val networkInfo: NetworkInfoService
) : EventsRepo {
    companion object {
        private const val UPDATE_TIME_POLITIC_SECONDS = 60 * 60 * 12
    }

    private val expTimer = ExpirationTimer.create(timerSharedPreferences, "event-info-tryForceUpdate")
    private val queryMaker: SearchQueryMaker = SQLSearchQueryMaker()


    override fun tryForceRefresh(): Single<UpdateResult> {
        return api.getAllEvents()
                .doAfterSuccess { events ->
                    val nextUpdateTime = DateTime.now().plusSeconds(UPDATE_TIME_POLITIC_SECONDS)
                    expTimer.rewind(nextUpdateTime)
                    saveEventsToDb(events)
                }.map(Response<*>::extractUpdateResult)
    }

    private fun saveEventsToDb(events: Response<EventBucketResp>) {
        if (!events.isSuccessful) {
            throw IllegalStateException("Network request failed")
        }
        dao.deleteAll()
        val eventBucket = events.body() ?: EventBucketResp()
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

    override fun softUpdate(): Single<UpdateResult> {
        return expTimer.isExpired.map { it && networkInfo.checkConnection() }.flatMap { needsUpdate ->
            if (needsUpdate) {
                tryForceRefresh()
            } else {
                Single.just(UpdateResult.NotUpdated)
            }
        }
    }

    private fun Observable<List<RoomEventInfo>>.convertToBusinessModel(): Observable<List<EventInfo>> {
        return map { eventList -> eventList.map { event -> event.convert() } }
    }


}