package com.org.home.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.org.design_system.theme.VpnColors
import com.org.design_system.theme.VpnDemoTheme
import com.org.design_system.theme.VpnTextStyle
import com.org.home.model.CountryDomain

@Composable
fun CountryListItem(
    country: CountryDomain,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val background = if (isSelected) VpnColors.Primary.copy(alpha = 0.1f) else Color.White.copy(alpha = 0.03f)
    val borderColor = if (isSelected) VpnColors.Primary.copy(alpha = 0.3f) else VpnColors.Border

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(background)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            FlagImage(
                flagUrl = country.flagUrl,
                countryName = country.countryName,
                size = 44.dp
            )

            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                    text = country.countryName,
                    style = VpnTextStyle.CountryName,
                    color = VpnColors.OnSurface
                )
                if (country.capital.isNotEmpty()) {
                    Text(
                        text = country.capital,
                        style = VpnTextStyle.CountryCapital,
                        color = VpnColors.Outline
                    )
                }
            }
        }

        if (isSelected) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Selected",
                tint = VpnColors.Primary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1923)
@Composable
private fun CountryListItemPreview() {
    VpnDemoTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            CountryListItem(
                country = CountryDomain(countryName = "Germany", capital = "Berlin", flagUrl = ""),
                isSelected = false,
                onClick = {}
            )
            CountryListItem(
                country = CountryDomain(countryName = "United States", capital = "Washington D.C.", flagUrl = ""),
                isSelected = true,
                onClick = {}
            )
        }
    }
}