package com.jotaro.catai.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
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

private val CatLightScheme = lightColorScheme(
    primary = Color(0xFFEE7C6B),
    secondary = Color(0xFF6A7BFF),
    tertiary = Color(0xFF4BD4B1),
    background = Color(0xFFF7F3FF),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF1B1C29),
    onSurface = Color(0xFF1B1C29),
)

@Composable
fun CatAiTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) CatDarkScheme else CatLightScheme
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
