package com.motoish.dayce.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.motoish.dayce.domain.DayCountFormatter
import com.motoish.dayce.domain.DayEventKind
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    viewModel: EventViewModel,
    eventId: Long,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDeleted: () -> Unit
) {
    val event by viewModel.observeEvent(eventId).collectAsStateWithLifecycle(initialValue = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } },
                actions = {
                    if (event != null) {
                        TextButton(onClick = onEdit) { Text("Edit") }
                    }
                }
            )
        }
    ) { padding ->
        val currentEvent = event
        if (currentEvent == null) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(24.dp)
            ) {
                Text("Event not found")
                TextButton(onClick = onBack) { Text("Back") }
            }
        } else {
            val countLabel = DayCountFormatter.label(currentEvent.kind, currentEvent.date, LocalDate.now())
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = currentEvent.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(text = countLabel, style = MaterialTheme.typography.displaySmall)
                Text(text = currentEvent.date.toString(), style = MaterialTheme.typography.titleMedium)
                Text(text = if (currentEvent.kind == DayEventKind.CountUp) "Count up" else "Countdown")
                currentEvent.note?.let { Text(text = it) }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(onClick = {
                        viewModel.deleteEvent(currentEvent)
                        onDeleted()
                    }) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}
