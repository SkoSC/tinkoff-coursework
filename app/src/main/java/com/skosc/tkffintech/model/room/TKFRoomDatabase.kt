package com.skosc.tkffintech.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skosc.tkffintech.model.room.adapter.DateTimeAdapter
import com.skosc.tkffintech.model.room.adapter.HomeworkCourseStatusAdapter
import com.skosc.tkffintech.model.room.adapter.HomeworkStatusAdapter
import com.skosc.tkffintech.model.room.adapter.HomeworkTaskTypeAdapter
import com.skosc.tkffintech.model.room.model.*

@TypeConverters(
        DateTimeAdapter::class,
        HomeworkStatusAdapter::class,
        HomeworkTaskTypeAdapter::class,
        HomeworkCourseStatusAdapter::class)
@Database(
        entities = [
            RoomEventInfo::class,
            RoomHomework::class,
            RoomHomeworkTask::class,
            RoomGrade::class,
            RoomUser::class,
            RoomCourseInfo::class,
            RoomUserCourseRelation::class
        ],
        version = 19
)
abstract class TKFRoomDatabase : RoomDatabase() {
    abstract val eventInfoDao: EventInfoDao
    abstract val homeworkDao: HomeworkDao
    abstract val gradesDao: GradesDao
    abstract val userDao: UserDao
    abstract val courseInfoDao: CourseInfoDao
}