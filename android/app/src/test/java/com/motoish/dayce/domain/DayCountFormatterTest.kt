package com.motoish.dayce.domain

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class DayCountFormatterTest {
    @Test
    fun countdownPastDateShowsDaysAgo() {
        val today = LocalDate.of(2026, 6, 24)
        val eventDate = LocalDate.of(2026, 6, 14)

        val result = DayCountFormatter.label(DayEventKind.Countdown, eventDate, today)

        assertEquals("10 days ago", result)
    }

    @Test
    fun countdownFutureDateShowsRemainingDays() {
        val today = LocalDate.of(2026, 6, 24)
        val eventDate = LocalDate.of(2026, 7, 4)

        val result = DayCountFormatter.label(DayEventKind.Countdown, eventDate, today)

        assertEquals("10 days", result)
    }

    @Test
    fun countdownTargetDateShowsZeroDays() {
        val today = LocalDate.of(2026, 6, 24)

        val result = DayCountFormatter.label(DayEventKind.Countdown, today, today)

        assertEquals("0 days", result)
    }

    @Test
    fun countUpPastDateKeepsPositiveElapsedDays() {
        val today = LocalDate.of(2026, 6, 24)
        val eventDate = LocalDate.of(2026, 6, 15)

        val result = DayCountFormatter.label(DayEventKind.CountUp, eventDate, today)

        assertEquals("10 days", result)
    }
}
