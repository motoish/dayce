package com.motoish.dayce.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.motoish.dayce.data.DayEventEntity
import com.motoish.dayce.data.DayEventRepository
import com.motoish.dayce.domain.DayEventKind
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class EventViewModel(private val repository: DayEventRepository) : ViewModel() {
    private val filter = MutableStateFlow(EventFilter.All)

    val selectedFilter: StateFlow<EventFilter> = filter

    val events: StateFlow<List<DayEventEntity>> = repository.observeAll()
        .combine(filter) { events, selectedFilter -> selectedFilter.apply(events) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun setFilter(next: EventFilter) {
        filter.value = next
    }

    fun observeEvent(id: Long) = repository.observeById(id)

    fun addEvent(name: String, date: LocalDate, kind: DayEventKind, note: String?) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.add(name, date, kind, note)
        }
    }

    fun updateEvent(event: DayEventEntity, name: String, date: LocalDate, kind: DayEventKind, note: String?) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.update(event, name, date, kind, note)
        }
    }

    fun deleteEvent(event: DayEventEntity) {
        viewModelScope.launch {
            repository.delete(event)
        }
    }

    class Factory(private val repository: DayEventRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EventViewModel(repository) as T
        }
    }
}
