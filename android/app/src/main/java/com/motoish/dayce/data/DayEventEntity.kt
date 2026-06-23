package com.motoish.dayce.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.motoish.dayce.domain.DayEventKind
import com.motoish.dayce.ui.FilterableEvent
import java.time.Instant
import java.time.LocalDate

@Entity(tableName = "day_events")
data class DayEventEntity(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0,
    val name: String,
    val date: LocalDate,
    override val kind: DayEventKind,
    val note: String?,
    val createdAt: Instant,
    val updatedAt: Instant
) : FilterableEvent
