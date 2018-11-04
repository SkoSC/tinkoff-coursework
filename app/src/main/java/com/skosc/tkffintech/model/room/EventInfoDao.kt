package com.skosc.tkffintech.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface EventInfoDao {
    @Query("SELECT * FROM `event_info`")
    fun all(): Single<List<RoomEventInfo>>

    @Query("SELECT * FROM `event_info` WHERE `is_active` == 1")
    fun getActiveEventInfo(): Observable<List<RoomEventInfo>>

    @Query("SELECT * FROM `event_info` WHERE `is_active` == 0")
    fun getArchiveEventInfo(): Observable<List<RoomEventInfo>>

    @Query("DELETE FROM `event_info`")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(infos: List<RoomEventInfo>)
}