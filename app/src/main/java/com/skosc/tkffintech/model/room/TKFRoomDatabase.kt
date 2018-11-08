package com.skosc.tkffintech.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skosc.tkffintech.model.room.adapter.DateTimeAdapter

@TypeConverters(DateTimeAdapter::class)
@Database(entities = [RoomEventInfo::class], version = 3)
abstract class TKFRoomDatabase : RoomDatabase() {
    abstract val eventInfoDao: EventInfoDao
}