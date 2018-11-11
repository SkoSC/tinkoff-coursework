package com.skosc.tkffintech.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skosc.tkffintech.model.room.adapter.DateTimeAdapter
import com.skosc.tkffintech.model.room.adapter.HomeworkStatusAdapter
import com.skosc.tkffintech.model.room.adapter.HomeworkTaskTypeAdapter

@TypeConverters(DateTimeAdapter::class, HomeworkStatusAdapter::class, HomeworkTaskTypeAdapter::class)
@Database(entities = [RoomEventInfo::class, RoomHomework::class, RoomHomeworkTask::class], version = 9)
abstract class TKFRoomDatabase : RoomDatabase() {
    abstract val eventInfoDao: EventInfoDao
    abstract val homeworkDao: HomeworkDao
}