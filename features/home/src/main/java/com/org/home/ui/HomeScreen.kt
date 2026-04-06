package com.org.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.org.design_system.theme.VpnColors
import com.org.design_system.theme.VpnDemoTheme
import com.org.home.compose.BottomSection
import com.org.home.compose.ConnectionSection
import com.org.home.compose.CountryBottomSheet
import com.org.home.compose.HomeTopBar
import com.org.home.model.CountryDomain
import com.org.vpn.model.VpnState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreenContent(
        state = state,
        handleEvent = viewModel::handleEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    state: HomeState,
    handleEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(VpnColors.Background)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            VpnColors.Primary.copy(alpha = 0.12f),
                            Color.Transparent
                        )
                    )
                )
        )

        Column(modifier = Modifier.fillMaxSize()) {
            HomeTopBar()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ConnectionSection(
                    vpnState = state.vpnState,
                    onOrbClick = { handleEvent(HomeEvent.OnConnectClicked) },
                    modifier = Modifier.padding(top = 48.dp)
                )

                BottomSection(
                    state = state,
                    onSelectCountry = { handleEvent(HomeEvent.OnSelectCountryClicked) }
                )
            }
        }

        if (state.isBottomSheetVisible) {
            CountryBottomSheet(
                countries = state.countries,
                selectedCountry = state.selectedCountry,
                searchQuery = state.searchQuery,
                sheetState = sheetState,
                onSearchQueryChanged = { handleEvent(HomeEvent.OnSearchQueryChanged(it)) },
                onCountrySelected = { handleEvent(HomeEvent.OnCountrySelected(it)) },
                onDismiss = { handleEvent(HomeEvent.OnBottomSheetDismissed) }
            )
        }
    }
}

@Preview(showBackground = false, backgroundColor = 0xFF0F1923)
@Composable
private fun HomeScreenDisconnectedPreview() {
    VpnDemoTheme {
        HomeScreenContent(
            state = HomeState(
                vpnState = VpnState.DISCONNECTED,
                selectedCountry = CountryDomain(countryName = "Germany", capital = "Berlin", flagUrl = "")
            ),
            handleEvent = {}
        )
    }
}

@Preview(showBackground = false, backgroundColor = 0xFF0F1923)
@Composable
private fun HomeScreenConnectedPreview() {
    VpnDemoTheme {
        HomeScreenContent(
            state = HomeState(
                vpnState = VpnState.CONNECTED,
                selectedCountry = CountryDomain(countryName = "Germany", capital = "Berlin", flagUrl = "")
            ),
            handleEvent = {}
        )
    }
}