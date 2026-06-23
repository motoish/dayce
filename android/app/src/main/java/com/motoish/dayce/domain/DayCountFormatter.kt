package com.motoish.dayce.domain

import java.time.LocalDate
import kotlin.math.abs

object DayCountFormatter {
    fun label(kind: DayEventKind, eventDate: LocalDate, today: LocalDate): String {
        val count = DayCalculator.dayCount(kind, eventDate, today)
        return if (kind == DayEventKind.Countdown && count < 0) {
            "${abs(count)} days ago"
        } else {
            "$count days"
        }
    }
}
