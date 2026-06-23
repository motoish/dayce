package com.motoish.dayce.domain

import java.time.LocalDate
import java.time.temporal.ChronoUnit

object DayCalculator {
    fun dayCount(kind: DayEventKind, eventDate: LocalDate, today: LocalDate): Long {
        return when (kind) {
            DayEventKind.CountUp -> {
                val days = ChronoUnit.DAYS.between(eventDate, today) + 1
                days.coerceAtLeast(0)
            }
            DayEventKind.Countdown -> ChronoUnit.DAYS.between(today, eventDate)
        }
    }
}
