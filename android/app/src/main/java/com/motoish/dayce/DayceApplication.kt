package com.motoish.dayce

import android.app.Application
import androidx.room.Room
import com.motoish.dayce.data.DayEventRepository
import com.motoish.dayce.data.DayceDatabase

class DayceApplication : Application() {
    val database: DayceDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            DayceDatabase::class.java,
            "dayce.db"
        ).build()
    }

    val repository: DayEventRepository by lazy {
        DayEventRepository(database.dayEventDao())
    }
}
