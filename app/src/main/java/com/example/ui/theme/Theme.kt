package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryTurfLight,
    onPrimary = PrimaryTurfDark,
    primaryContainer = PrimaryTurfContainer,
    onPrimaryContainer = Color(0xFF003918),
    secondary = SecondaryFloodlightLight,
    onSecondary = SecondaryFloodlightDark,
    secondaryContainer = SecondaryFloodlightAmber,
    onSecondaryContainer = Color(0xFF432C00),
    tertiary = TertiaryMintLight,
    onTertiary = Color(0xFF003824),
    tertiaryContainer = TertiaryMint,
    onTertiaryContainer = Color(0xFF003824),
    background = PitchNightBase,
    onBackground = TextPrimary,
    surface = PitchCardSurface,
    onSurface = TextPrimary,
    surfaceVariant = PitchCardActive,
    onSurfaceVariant = TextMuted,
    outline = PitchCardBorder,
    outlineVariant = OutlineVariant,
    error = StatusBookedRed,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep consistent athletic dark-pitch brand aesthetics
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
