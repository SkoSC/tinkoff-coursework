package com.skosc.tkffintech.model.repo

import android.content.SharedPreferences
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.model.room.EventInfoDao
import com.skosc.tkffintech.model.room.RoomEventInfo
import com.skosc.tkffintech.model.webservice.TinkoffEventsApi
import com.skosc.tkffintech.utils.mapEach
import io.reactivex.Single
import org.joda.time.DateTime

class EventsRepoImpl(private val api: TinkoffEventsApi, private val dao: EventInfoDao, private val timerSharedPreferences: SharedPreferences) : EventsRepo {
    private val rxSharedPreferences = RxSharedPreferences.create(timerSharedPreferences)
    private val lastUpdatedPref = rxSharedPreferences.getLong("timer-event-info-update", 0)

    override fun refresh() {
        Single.create<Long> { lastUpdatedPref.get() }
                .filter { lastUpdated -> lastUpdated > DateTime.now().minusHours(1).millis }
                .subscribe {
                    forceRefresh()
                }
    }

    override fun forceRefresh() {
        api.getAllEvents().subscribe { events ->
            dao.deleteAll()
            val eventBucket = events.body() ?: TinkoffEventsApi.EventBucket()
            val active = eventBucket.active.map { RoomEventInfo.from(it, true) }
            val archive = eventBucket.archive.map { RoomEventInfo.from(it, false) }
            dao.insertAll(active + archive)
            lastUpdatedPref.set(DateTime.now().millis)
        }
    }

    override fun getOnGoingEvents(): Single<List<EventInfo>> {
        refresh()
        return dao.getActiveEventInfo().first(listOf()).mapEach {
            it.convert()
        }
    }

    override fun getArchiveEvents(): Single<List<EventInfo>> {
        refresh()
        return dao.getArchiveEventInfo().first(listOf()).mapEach {
            it.convert()
        }
    }

    override fun findEventByHid(hid: Long): Single<EventInfo> {
        return dao.searchForEventWithId(hid)
                .map(RoomEventInfo::convert)
                .firstOrError()
    }
}