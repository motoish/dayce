package com.motoish.dayce.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DayceColors = lightColorScheme(
    primary = Color(0xFF2563EB),
    secondary = Color(0xFF0F766E),
    tertiary = Color(0xFFB45309),
    surface = Color(0xFFFFFBFE),
    background = Color(0xFFFFFBFE)
)

@Composable
fun DayceTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DayceColors,
        typography = MaterialTheme.typography,
        content = content
    )
}
