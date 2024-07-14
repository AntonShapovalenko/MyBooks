package com.books.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorPalette = lightColorScheme(
    primary = Pink,
    background = Background,
    onBackground = Color.White,
    onPrimary = Color.White,
    surface = Color.White,
    onSurface = Blackberry
)


@Composable
fun MyBooksTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColorPalette,
        typography = MaterialTheme.typography.defaultFontFamily(fontFamily = NunitoSans),
        content = content
    )
}