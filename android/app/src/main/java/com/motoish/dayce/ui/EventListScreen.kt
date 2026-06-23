package com.motoish.dayce.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.motoish.dayce.data.DayEventEntity
import com.motoish.dayce.domain.DayCountFormatter
import com.motoish.dayce.domain.DayEventKind
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    viewModel: EventViewModel,
    onAdd: () -> Unit,
    onOpen: (Long) -> Unit
) {
    val events by viewModel.events.collectAsStateWithLifecycle()
    val selectedFilter by viewModel.selectedFilter.collectAsStateWithLifecycle()
    val today = LocalDate.now()

    Scaffold(
        topBar = { TopAppBar(title = { Text("dayce") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdd,
                modifier = Modifier.size(64.dp)
            ) {
                Text("+", fontSize = 32.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            FilterRow(
                selected = selectedFilter,
                onSelected = viewModel::setFilter
            )
            if (events.isEmpty()) {
                Text(
                    text = "No events",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(24.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(events, key = { it.id }) { event ->
                        EventRow(
                            event = event,
                            today = today,
                            onClick = { onOpen(event.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterRow(selected: EventFilter, onSelected: (EventFilter) -> Unit) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        EventFilter.entries.forEach { filter ->
            FilterChip(
                selected = filter == selected,
                onClick = { onSelected(filter) },
                label = {
                    Text(
                        text = filter.label,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                modifier = Modifier.defaultMinSize(minHeight = 48.dp)
            )
        }
    }
}

@Composable
private fun EventRow(
    event: DayEventEntity,
    today: LocalDate,
    onClick: () -> Unit
) {
    val countLabel = DayCountFormatter.label(event.kind, event.date, today)
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = event.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                AssistChip(
                    onClick = {},
                    label = { Text(if (event.kind == DayEventKind.CountUp) "Count up" else "Countdown") }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = event.date.toString(), style = MaterialTheme.typography.bodyMedium)
            Text(text = countLabel, style = MaterialTheme.typography.headlineSmall)
        }
    }
}

private val EventFilter.label: String
    get() = when (this) {
        EventFilter.All -> "All"
        EventFilter.CountUp -> "Count up"
        EventFilter.Countdown -> "Countdown"
    }
