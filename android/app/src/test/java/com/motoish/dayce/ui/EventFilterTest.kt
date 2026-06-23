package com.motoish.dayce.ui

import com.motoish.dayce.domain.DayEventKind
import org.junit.Assert.assertEquals
import org.junit.Test

class EventFilterTest {
    @Test
    fun allFilterKeepsEveryEvent() {
        val events = listOf(
            TestFilterableEvent(1, DayEventKind.CountUp),
            TestFilterableEvent(2, DayEventKind.Countdown)
        )

        val result = EventFilter.All.apply(events)

        assertEquals(listOf(1L, 2L), result.map { it.id })
    }

    @Test
    fun countUpFilterKeepsOnlyCountUpEvents() {
        val events = listOf(
            TestFilterableEvent(1, DayEventKind.CountUp),
            TestFilterableEvent(2, DayEventKind.Countdown)
        )

        val result = EventFilter.CountUp.apply(events)

        assertEquals(listOf(1L), result.map { it.id })
    }

    @Test
    fun countdownFilterKeepsOnlyCountdownEvents() {
        val events = listOf(
            TestFilterableEvent(1, DayEventKind.CountUp),
            TestFilterableEvent(2, DayEventKind.Countdown)
        )

        val result = EventFilter.Countdown.apply(events)

        assertEquals(listOf(2L), result.map { it.id })
    }

    private data class TestFilterableEvent(
        override val id: Long,
        override val kind: DayEventKind
    ) : FilterableEvent
}
