package com.skosc.tkffintech.model.repo

import android.content.SharedPreferences
import android.util.Log
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.misc.DataUpdateResult
import com.skosc.tkffintech.model.entity.ExpirationTimer
import com.skosc.tkffintech.model.room.EventInfoDao
import com.skosc.tkffintech.model.room.model.RoomEventInfo
import com.skosc.tkffintech.model.webservice.TinkoffEventsApi
import com.skosc.tkffintech.service.NetworkInfoService
import com.skosc.tkffintech.utils.SQLSearchQueryMaker
import com.skosc.tkffintech.utils.SearchQueryMaker
import com.skosc.tkffintech.utils.own
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.joda.time.DateTime
import retrofit2.Response

class EventsRepoImpl(
        private val api: TinkoffEventsApi,
        private val dao: EventInfoDao,
        private val timerSharedPreferences: SharedPreferences,
        private val networkInfo: NetworkInfoService
) : EventsRepo {
    companion object {
        private const val UPDATE_TIME_POLITIC_SECONDS = 60 * 60 * 12
        private const val TAG = "TKF_REPO_EVENTS"
    }

    private val cdisp = CompositeDisposable()
    private val expTimer = ExpirationTimer.create(timerSharedPreferences, "event-info-tryForceUpdate")
    private val queryMaker: SearchQueryMaker = SQLSearchQueryMaker()


    override fun tryForceRefresh(): Single<Boolean> {
        return api.getAllEvents()
                .doAfterSuccess { events ->
                    val nextUpdateTime = DateTime.now().plusSeconds(UPDATE_TIME_POLITIC_SECONDS)
                    expTimer.rewind(nextUpdateTime)
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
        performSoftUpdate()
        val sqlQuery = queryMaker.from(query, mode)
        return dao.search(sqlQuery, isOnGoing).map { it.map { it.convert() } }
    }

    override val onGoingEvents: Observable<List<EventInfo>> by lazy {
        performSoftUpdate()
        dao.getActiveEventInfo().convertToBusinessModel()
    }

    override val archiveEvents: Observable<List<EventInfo>> by lazy {
        performSoftUpdate()
        dao.getArchiveEventInfo().convertToBusinessModel()
    }

    override fun findEventByHid(hid: Long): Single<EventInfo> {
        performSoftUpdate()
        return dao.searchForEventWithId(hid)
                .map(RoomEventInfo::convert)
                .firstOrError()
    }

    private fun softUpdate(): Single<DataUpdateResult> {
        return expTimer.isExpired.map { it && networkInfo.checkConnection() }.flatMap { needsUpdate ->
            if (needsUpdate) {
                tryForceRefresh().map(this::successToUpdateResult)
            } else {
                Single.just(DataUpdateResult.NotUpdated)
            }
        }
    }

    override fun performSoftUpdate() {
        cdisp own softUpdate().subscribe { res -> Log.d(TAG, "Update result: $res") }
    }

    private fun successToUpdateResult(success: Boolean): DataUpdateResult {
        return if (success) {
            DataUpdateResult.Updated
        } else {
            DataUpdateResult.Error
        }
    }

    private fun Observable<List<RoomEventInfo>>.convertToBusinessModel(): Observable<List<EventInfo>> {
        return map { eventList -> eventList.map { event -> event.convert() } }
    }


}