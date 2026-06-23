package com.motoish.dayce.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DayEventEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DayceDatabase : RoomDatabase() {
    abstract fun dayEventDao(): DayEventDao
}
