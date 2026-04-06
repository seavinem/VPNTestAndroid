package com.org.design_system.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object VpnTextStyle {
    val SheetTitle = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
    val AppTitle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.3).sp
    )
    val StatusTitle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    )
    val StatusSubtitle = TextStyle(
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 2.sp
    )
    val SectionLabel = TextStyle(
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 2.sp
    )
    val CountryName = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    )
    val CountryCapital = TextStyle(
        fontSize = 12.sp
    )
    val FlagLetter = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
    val InfoLabel = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 1.5.sp
    )
    val InfoValue = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    )
}