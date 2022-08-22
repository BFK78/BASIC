package com.example.basic.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.basic.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = primary,
    primaryVariant = primary_dark1,
    secondary = secondary,
    background = primary_dark3,
    surface = primary_dark2,
    onPrimary = primary_variant1,
    onSecondary = primary_dark3,
    onBackground = primary_variant2,
    onSurface = primary_variant3,
    error = error
)

private val LightColorPalette = lightColors(
    primary = primary,
    primaryVariant = primary_dark1,
    secondary = secondary,
    background = primary_dark3,
    surface = primary_dark2,
    onPrimary = primary_variant1,
    onSecondary = primary_dark3,
    onBackground = primary_variant2,
    onSurface = primary_variant3,
    error = error
)

val prata = FontFamily(Font(R.font.prata))

val zillaSlab = FontFamily(Font(R.font.zillaslabbold,weight = FontWeight.Bold),
Font(R.font.zillaslablight, weight = FontWeight.Light),
Font(R.font.zillaslabmedium, weight = FontWeight.Medium),
Font(R.font.zillaslabregular, weight = FontWeight.Normal),
Font(R.font.zillaslabsemibold, weight = FontWeight.SemiBold))

@Composable
fun BASICTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) {
        systemUiController.setSystemBarsColor(
            MaterialTheme.colors.onSecondary
        )
        DarkColorPalette
    } else {
        systemUiController.setSystemBarsColor(
            MaterialTheme.colors.onSecondary
        )
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}