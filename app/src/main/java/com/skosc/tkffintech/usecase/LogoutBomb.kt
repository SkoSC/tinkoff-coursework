package com.skosc.tkffintech.usecase

import androidx.room.RoomDatabase

class LogoutBomb(private val db: RoomDatabase) {
    fun perform() {
        db.clearAllTables()
    }
}