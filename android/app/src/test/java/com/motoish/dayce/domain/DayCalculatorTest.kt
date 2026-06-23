package com.motoish.dayce.domain

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class DayCalculatorTest {
    @Test
    fun countUpIncludesEventDateAsDayOne() {
        val today = LocalDate.of(2026, 6, 24)
        val eventDate = LocalDate.of(2026, 6, 24)

        val result = DayCalculator.dayCount(DayEventKind.CountUp, eventDate, today)

        assertEquals(1, result)
    }

    @Test
    fun countUpTomorrowIsZero() {
        val today = LocalDate.of(2026, 6, 24)
        val eventDate = LocalDate.of(2026, 6, 25)

        val result = DayCalculator.dayCount(DayEventKind.CountUp, eventDate, today)

        assertEquals(0, result)
    }

    @Test
    fun countdownTargetDateIsZero() {
        val today = LocalDate.of(2026, 6, 24)
        val targetDate = LocalDate.of(2026, 6, 24)

        val result = DayCalculator.dayCount(DayEventKind.Countdown, targetDate, today)

        assertEquals(0, result)
    }

    @Test
    fun countdownFutureDateIsPositive() {
        val today = LocalDate.of(2026, 6, 24)
        val targetDate = LocalDate.of(2026, 6, 30)

        val result = DayCalculator.dayCount(DayEventKind.Countdown, targetDate, today)

        assertEquals(6, result)
    }

    @Test
    fun countdownPastDateIsNegative() {
        val today = LocalDate.of(2026, 6, 24)
        val targetDate = LocalDate.of(2026, 6, 20)

        val result = DayCalculator.dayCount(DayEventKind.Countdown, targetDate, today)

        assertEquals(-4, result)
    }
}
