package com.example.jeepni.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.jeepni.R


val quicksandFontFamily = FontFamily(
    Font(R.font.quicksand_regular),
    Font(R.font.quicksand_bold),
    Font(R.font.quicksand_medium),
    Font(R.font.quicksand_light),
    Font(R.font.quicksand_semibold)
)
// Set of Material typography styles to start with
val JeepNiTypography = Typography(
    bodyMedium = TextStyle(
        fontFamily = quicksandFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)