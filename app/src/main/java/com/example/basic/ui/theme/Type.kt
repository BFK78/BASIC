package com.example.basic.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.basic.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = zillaSlab,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = primary_variant3
    ),
    h5 = TextStyle(
        fontFamily = prata,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        color = primary_variant3
    ),
    h4 = TextStyle(
        fontFamily = zillaSlab,
        fontWeight = FontWeight.SemiBold,
        fontSize = 34.sp,
        color = primary_variant3
    ),
    h6 = TextStyle(
        fontFamily = zillaSlab,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = primary_variant3
    )

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
