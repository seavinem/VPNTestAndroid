package com.org.home.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.org.design_system.theme.VpnColors
import com.org.design_system.theme.VpnDemoTheme
import com.org.features.home.R
import com.org.design_system.theme.VpnTextStyle
import com.org.home.model.CountryDomain
import com.org.home.ui.HomeState
import com.org.vpn.model.VpnState

@Composable
fun BottomSection(
    state: HomeState,
    onSelectCountry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = stringResource(R.string.home_smart_location),
            style = VpnTextStyle.SectionLabel,
            color = VpnColors.Outline,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        if (state.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = stringResource(R.string.home_loading_locations),
                    style = VpnTextStyle.CountryCapital,
                    color = VpnColors.OnSurface.copy(alpha = 0.72f)
                )
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(100.dp)),
                    color = VpnColors.Primary,
                    trackColor = Color.White.copy(alpha = 0.08f)
                )
            }
        }

        CountryCard(
            country = state.selectedCountry,
            onClick = onSelectCountry
        )

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            InfoCard(
                label = stringResource(R.string.home_protocol_label),
                value = stringResource(R.string.home_protocol_value),
                modifier = Modifier.weight(1f)
            )
            InfoCard(
                label = stringResource(R.string.home_ip_address_label),
                value = stringResource(
                    if (state.vpnState == VpnState.CONNECTED) {
                        R.string.home_ip_hidden
                    } else {
                        R.string.home_ip_exposed
                    }
                ),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1923)
@Composable
private fun BottomSectionDisconnectedPreview() {
    VpnDemoTheme {
        BottomSection(
            state = HomeState(
                isLoading = true,
                vpnState = VpnState.DISCONNECTED,
                selectedCountry = CountryDomain(countryName = "Germany", capital = "Berlin", flagUrl = "")
            ),
            onSelectCountry = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1923)
@Composable
private fun BottomSectionConnectedPreview() {
    VpnDemoTheme {
        BottomSection(
            state = HomeState(
                isLoading = true,
                vpnState = VpnState.CONNECTED,
                selectedCountry = CountryDomain(countryName = "Germany", capital = "Berlin", flagUrl = "")
            ),
            onSelectCountry = {}
        )
    }
}
