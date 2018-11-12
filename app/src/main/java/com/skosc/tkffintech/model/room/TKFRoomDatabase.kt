package com.skosc.tkffintech.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skosc.tkffintech.model.room.adapter.DateTimeAdapter
import com.skosc.tkffintech.model.room.adapter.HomeworkStatusAdapter
import com.skosc.tkffintech.model.room.adapter.HomeworkTaskTypeAdapter
import com.skosc.tkffintech.model.room.model.*

@TypeConverters(DateTimeAdapter::class, HomeworkStatusAdapter::class, HomeworkTaskTypeAdapter::class)
@Database(
        entities = [
            RoomEventInfo::class,
            RoomHomework::class,
            RoomHomeworkTask::class,
            RoomGrade::class,
            RoomUser::class
        ],
        version = 15
)
abstract class TKFRoomDatabase : RoomDatabase() {
    abstract val eventInfoDao: EventInfoDao
    abstract val homeworkDao: HomeworkDao
    abstract val gradesDao: GradesDao
    abstract val userDao: UserDao
}