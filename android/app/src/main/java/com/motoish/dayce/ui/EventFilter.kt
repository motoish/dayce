package com.motoish.dayce.ui

import com.motoish.dayce.domain.DayEventKind

interface FilterableEvent {
    val id: Long
    val kind: DayEventKind
}

enum class EventFilter {
    All,
    CountUp,
    Countdown;

    fun <T : FilterableEvent> apply(events: List<T>): List<T> {
        return when (this) {
            All -> events
            CountUp -> events.filter { it.kind == DayEventKind.CountUp }
            Countdown -> events.filter { it.kind == DayEventKind.Countdown }
        }
    }
}
