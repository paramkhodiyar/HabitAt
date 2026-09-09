package com.habbitat.app.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = SaffronPrimary,
    onPrimary = InkPrimary,
    primaryContainer = SaffronLight,
    onPrimaryContainer = InkPrimary,
    secondary = IndigoSecondary,
    onSecondary = CardSurface,
    secondaryContainer = IndigoLight,
    onSecondaryContainer = IndigoSecondary,
    tertiary = TerracottaTertiary,
    onTertiary = CardSurface,
    tertiaryContainer = TerracottaLight,
    onTertiaryContainer = TerracottaTertiary,
    background = WarmIvory,
    onBackground = InkPrimary,
    surface = CardSurface,
    onSurface = InkPrimary,
    surfaceVariant = WarmIvory,
    onSurfaceVariant = InkSecondary,
    outline = GlassBorder,
    outlineVariant = LineArtMuted
)

@Composable
fun HabbitAtTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = HabbitAtTypography,
        content = content
    )
}
