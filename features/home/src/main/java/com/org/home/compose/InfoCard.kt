package com.org.home.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.org.design_system.theme.VpnColors
import com.org.design_system.theme.VpnDemoTheme
import com.org.design_system.theme.VpnTextStyle

@Composable
fun InfoCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.03f))
            .border(1.dp, VpnColors.Border, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = label.uppercase(),
            style = VpnTextStyle.InfoLabel,
            color = VpnColors.Outline
        )
        Text(
            text = value,
            style = VpnTextStyle.InfoValue,
            color = VpnColors.OnSurface
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1923)
@Composable
private fun InfoCardPreview() {
    VpnDemoTheme {
        InfoCard(label = "Protocol", value = "WireGuard®")
    }
}