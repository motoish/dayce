package com.motoish.dayce.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.motoish.dayce.data.DayEventEntity
import com.motoish.dayce.domain.DayEventKind
import java.time.LocalDate
import java.time.format.DateTimeParseException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventFormScreen(
    title: String,
    existing: DayEventEntity?,
    onCancel: () -> Unit,
    onSave: (String, LocalDate, DayEventKind, String?) -> Unit
) {
    var name by remember(existing?.id) { mutableStateOf(existing?.name.orEmpty()) }
    var dateText by remember(existing?.id) { mutableStateOf((existing?.date ?: LocalDate.now()).toString()) }
    var kind by remember(existing?.id) { mutableStateOf(existing?.kind ?: DayEventKind.CountUp) }
    var note by remember(existing?.id) { mutableStateOf(existing?.note.orEmpty()) }
    val parsedDate = remember(dateText) { dateText.toLocalDateOrNull() }
    val canSave = name.isNotBlank() && parsedDate != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = { TextButton(onClick = onCancel) { Text("Cancel") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = dateText,
                onValueChange = { dateText = it },
                label = { Text("Date") },
                supportingText = { Text("yyyy-MM-dd") },
                isError = parsedDate == null,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = kind == DayEventKind.CountUp,
                    onClick = { kind = DayEventKind.CountUp },
                    label = { Text("Count up") }
                )
                FilterChip(
                    selected = kind == DayEventKind.Countdown,
                    onClick = { kind = DayEventKind.Countdown },
                    label = { Text("Countdown") }
                )
            }
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Note") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { onSave(name, parsedDate ?: LocalDate.now(), kind, note) },
                enabled = canSave,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}

@Composable
fun EventEditRoute(
    viewModel: EventViewModel,
    eventId: Long,
    onCancel: () -> Unit,
    onSaved: () -> Unit
) {
    val event by viewModel.observeEvent(eventId).collectAsStateWithLifecycle(initialValue = null)
    val existing = event

    if (existing == null) {
        TextButton(onClick = onCancel) { Text("Event not found") }
    } else {
        EventFormScreen(
            title = "Edit event",
            existing = existing,
            onCancel = onCancel,
            onSave = { name, date, kind, note ->
                viewModel.updateEvent(existing, name, date, kind, note)
                onSaved()
            }
        )
    }
}

private fun String.toLocalDateOrNull(): LocalDate? {
    return try {
        LocalDate.parse(this)
    } catch (_: DateTimeParseException) {
        null
    }
}
