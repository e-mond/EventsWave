package com.eventwave.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Brand Colors
val AccentGreen = Color(0xFF95FF2C)
val BackgroundGreen = Color(0xFFECFFD9)
val PureBlack = Color(0xFF000000)
val PureWhite = Color(0xFFFFFFFF)

// Gray Scale
val Gray50 = Color(0xFFF9FAFB)
val Gray100 = Color(0xFFF3F4F6)
val Gray200 = Color(0xFFE5E7EB)
val Gray300 = Color(0xFFD1D5DB)
val Gray400 = Color(0xFF9CA3AF)
val Gray500 = Color(0xFF6B7280)
val Gray600 = Color(0xFF4B5563)
val Gray700 = Color(0xFF374151)
val Gray800 = Color(0xFF1F2937)
val Gray900 = Color(0xFF111827)

// Dark mode colors
val DarkSurface = Color(0xFF1A1A1A)
val DarkBackground = Color(0xFF121212)

private val DarkColorScheme = darkColorScheme(
    primary = AccentGreen,
    onPrimary = PureBlack,
    primaryContainer = Gray800,
    onPrimaryContainer = AccentGreen,
    secondary = Gray400,
    onSecondary = Gray900,
    secondaryContainer = Gray700,
    onSecondaryContainer = Gray200,
    tertiary = AccentGreen,
    onTertiary = PureBlack,
    tertiaryContainer = Gray800,
    onTertiaryContainer = AccentGreen,
    background = DarkBackground,
    onBackground = PureWhite,
    surface = DarkSurface,
    onSurface = PureWhite,
    surfaceVariant = Gray800,
    onSurfaceVariant = Gray300,
    outline = Gray600,
    outlineVariant = Gray700
)

private val LightColorScheme = lightColorScheme(
    primary = AccentGreen,
    onPrimary = PureBlack,
    primaryContainer = BackgroundGreen,
    onPrimaryContainer = PureBlack,
    secondary = Gray600,
    onSecondary = PureWhite,
    secondaryContainer = Gray100,
    onSecondaryContainer = PureBlack,
    tertiary = AccentGreen,
    onTertiary = PureBlack,
    tertiaryContainer = BackgroundGreen,
    onTertiaryContainer = PureBlack,
    background = BackgroundGreen,
    onBackground = PureBlack,
    surface = PureWhite,
    onSurface = PureBlack,
    surfaceVariant = Gray50,
    onSurfaceVariant = Gray600,
    outline = Gray300,
    outlineVariant = Gray200
)

@Composable
fun EventWaveTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}