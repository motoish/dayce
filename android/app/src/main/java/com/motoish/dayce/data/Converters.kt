package com.motoish.dayce.data

import androidx.room.TypeConverter
import com.motoish.dayce.domain.DayEventKind
import java.time.Instant
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun localDateToString(value: LocalDate): String = value.toString()

    @TypeConverter
    fun stringToLocalDate(value: String): LocalDate = LocalDate.parse(value)

    @TypeConverter
    fun instantToString(value: Instant): String = value.toString()

    @TypeConverter
    fun stringToInstant(value: String): Instant = Instant.parse(value)

    @TypeConverter
    fun eventKindToString(value: DayEventKind): String = value.name

    @TypeConverter
    fun stringToEventKind(value: String): DayEventKind = DayEventKind.valueOf(value)
}
