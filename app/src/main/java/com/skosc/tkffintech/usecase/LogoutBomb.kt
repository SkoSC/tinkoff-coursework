package com.skosc.tkffintech.usecase

import android.content.SharedPreferences
import androidx.room.RoomDatabase

class LogoutBomb(private val db: RoomDatabase, private val sp: List<SharedPreferences>) {
    fun perform() {
        db.clearAllTables()
        sp.forEach { it.edit().clear().apply() }
    }
}