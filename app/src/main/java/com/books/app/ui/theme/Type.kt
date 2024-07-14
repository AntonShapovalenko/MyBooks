package com.books.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.books.app.R

val Georgia = FontFamily(
    Font(
        resId = R.font.georgia_italic,
        weight = FontWeight.Normal
    )
)
val NunitoSans = FontFamily(
    Font(
        resId = R.font.nunito_sans_bold,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.nunito_sans_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.nunito_sans_light,
        weight = FontWeight.Light
    )
)
fun Typography.defaultFontFamily(fontFamily: FontFamily): Typography {
    return this.copy(
        displayLarge = this.displayLarge.copy(fontFamily = fontFamily),
        displayMedium = this.displayMedium.copy(fontFamily = fontFamily),
        displaySmall = this.displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = this.headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = this.headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = this.headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = this.titleLarge.copy(fontFamily = fontFamily),
        titleMedium = this.titleMedium.copy(fontFamily = fontFamily),
        titleSmall = this.titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = this.bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = this.bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = this.bodySmall.copy(fontFamily = fontFamily),
        labelLarge = this.labelLarge.copy(fontFamily = fontFamily),
        labelMedium = this.labelMedium.copy(fontFamily = fontFamily),
        labelSmall = this.labelSmall.copy(fontFamily = fontFamily)
    )
}

// Set of Material typography styles to start with
val TypographyStyle = Typography(

    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    ),
/* Other default text styles to override
labelSmall = TextStyle(
  fontFamily = FontFamily.Default,
  fontWeight = FontWeight.Medium,
  fontSize = 11.sp,
  lineHeight = 16.sp,
  letterSpacing = 0.5.sp
)
*/
)