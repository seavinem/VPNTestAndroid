package com.org.home.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.org.design_system.theme.VpnColors
import com.org.design_system.theme.VpnDemoTheme
import com.org.design_system.theme.VpnTextStyle
import com.org.home.model.CountryDomain
import com.org.home.model.VpnState
import com.org.home.ui.HomeState

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
            text = "Smart Location",
            style = VpnTextStyle.SectionLabel,
            color = VpnColors.Outline,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        CountryCard(
            country = state.selectedCountry,
            onClick = onSelectCountry
        )

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            InfoCard(
                label = "Protocol",
                value = "WireGuard®",
                modifier = Modifier.weight(1f)
            )
            InfoCard(
                label = "IP Address",
                value = if (state.vpnState == VpnState.CONNECTED) "Hidden" else "Exposed",
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
                vpnState = VpnState.CONNECTED,
                selectedCountry = CountryDomain(countryName = "Germany", capital = "Berlin", flagUrl = "")
            ),
            onSelectCountry = {}
        )
    }
}