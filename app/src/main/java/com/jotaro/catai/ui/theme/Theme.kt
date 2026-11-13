package com.jotaro.catai.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CatDarkScheme = darkColorScheme(
    primary = Color(0xFFEE7C6B),
    secondary = Color(0xFF9DA9FF),
    tertiary = Color(0xFF8BE0D1),
    background = Color(0xFF090B16),
    surface = Color(0xFF121827),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFFF3F6FF),
    onSurface = Color(0xFFF3F6FF),
)

@Composable
fun CatAiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CatDarkScheme,
        content = content
    )
}
