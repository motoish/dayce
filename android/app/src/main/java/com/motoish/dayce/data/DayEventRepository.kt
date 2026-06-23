package com.motoish.dayce.data

import com.motoish.dayce.domain.DayEventKind
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.LocalDate

class DayEventRepository(private val dao: DayEventDao) {
    fun observeAll(): Flow<List<DayEventEntity>> = dao.observeAll()

    fun observeById(id: Long): Flow<DayEventEntity?> = dao.observeById(id)

    suspend fun add(name: String, date: LocalDate, kind: DayEventKind, note: String?) {
        val now = Instant.now()
        dao.insert(
            DayEventEntity(
                name = name.trim(),
                date = date,
                kind = kind,
                note = note?.trim()?.ifBlank { null },
                createdAt = now,
                updatedAt = now
            )
        )
    }

    suspend fun update(existing: DayEventEntity, name: String, date: LocalDate, kind: DayEventKind, note: String?) {
        dao.update(
            existing.copy(
                name = name.trim(),
                date = date,
                kind = kind,
                note = note?.trim()?.ifBlank { null },
                updatedAt = Instant.now()
            )
        )
    }

    suspend fun delete(event: DayEventEntity) {
        dao.delete(event)
    }
}
