package com.noa.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(

    primary = PrimaryGreen,
    secondary = SecondaryBlue,
    tertiary = AccentGold,

    background = Background,
    surface = Surface,

    onPrimary = TextOnPrimary,
    onSecondary = TextOnPrimary,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

private val DarkColors = darkColorScheme(

    primary = PrimaryGreenLight,
    onPrimary = Color(0xFF0F2415),

    primaryContainer = Color(0xFF1F4D2B),
    onPrimaryContainer = Color(0xFFD7F8DD),

    secondary = SecondaryBlueLight,
    onSecondary = Color(0xFF101B33),

    secondaryContainer = Color(0xFF263B68),
    onSecondaryContainer = Color(0xFFDCE7FF),

    tertiary = AccentGold,
    onTertiary = Color(0xFF2A2000),

    background = DarkBackground,
    onBackground = DarkTextPrimary,

    surface = DarkSurface,
    onSurface = DarkTextPrimary,

    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,

    outline = DarkCardBorder,
    outlineVariant = DarkDivider
)

@Composable
fun NoATheme(

    darkTheme: Boolean = false,

    content: @Composable () -> Unit

) {

    val colors =

        if (darkTheme)
            DarkColors
        else
            LightColors

    MaterialTheme(

        colorScheme =
            colors,

        typography =
            Typography,

        content =
            content

    )

}