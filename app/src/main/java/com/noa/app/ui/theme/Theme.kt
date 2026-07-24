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

    primary = PrimaryGreen,
    secondary = SecondaryBlue,
    tertiary = AccentGold,

    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),

    onPrimary = TextOnPrimary,
    onSecondary = TextOnPrimary,
    onBackground = Color.White,
    onSurface = Color.White
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