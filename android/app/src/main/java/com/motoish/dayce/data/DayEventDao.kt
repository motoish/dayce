package com.motoish.dayce.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DayEventDao {
    @Query("SELECT * FROM day_events ORDER BY date ASC, name COLLATE NOCASE ASC")
    fun observeAll(): Flow<List<DayEventEntity>>

    @Query("SELECT * FROM day_events WHERE id = :id LIMIT 1")
    fun observeById(id: Long): Flow<DayEventEntity?>

    @Insert
    suspend fun insert(event: DayEventEntity): Long

    @Update
    suspend fun update(event: DayEventEntity)

    @Delete
    suspend fun delete(event: DayEventEntity)
}
