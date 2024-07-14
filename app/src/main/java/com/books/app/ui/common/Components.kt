package com.books.app.ui.common

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.books.app.R
import com.books.app.data.model.GenreType

@Composable
fun GenreType.title(): String {
    return when (this) {
        GenreType.Fantasy -> stringResource(id = R.string.genre_fantasy)
        GenreType.Romance -> stringResource(id = R.string.genre_romance)
        GenreType.Science -> stringResource(id = R.string.genre_science)
    }
}

val ZeroWindowInsets = WindowInsets(0, 0, 0, 0)

