package com.org.home.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.org.design_system.theme.VpnColors
import com.org.design_system.theme.VpnTextStyle

@Composable
fun FlagImage(
    flagUrl: String,
    countryName: String,
    size: Dp = 44.dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.06f))
            .border(1.dp, VpnColors.Border, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (flagUrl.isNotEmpty()) {
            AsyncImage(
                model = flagUrl,
                contentDescription = "$countryName flag",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
            )
        } else {
            Text(
                text = countryName.firstOrNull()?.toString() ?: "?",
                style = VpnTextStyle.FlagLetter,
                color = VpnColors.Primary
            )
        }
    }
}