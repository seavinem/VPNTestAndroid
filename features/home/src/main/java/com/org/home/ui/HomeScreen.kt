package com.org.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.org.design_system.theme.VpnColors
import com.org.design_system.theme.VpnDemoTheme
import com.org.features.home.R
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
    var isConnectionErrorVisible by remember { mutableStateOf(false) }
    var isCountriesErrorVisible by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                HomeEffect.OnOpenBottomSheet -> Unit
                HomeEffect.ShowConnectionError -> isConnectionErrorVisible = true
                HomeEffect.ShowCountriesError -> isCountriesErrorVisible = true
            }
        }
    }

    HomeScreenContent(
        state = state,
        handleEvent = viewModel::handleEvent,
        isConnectionErrorVisible = isConnectionErrorVisible,
        onConnectionErrorDismissed = { isConnectionErrorVisible = false },
        isCountriesErrorVisible = isCountriesErrorVisible,
        onCountriesErrorDismissed = { isCountriesErrorVisible = false }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    state: HomeState,
    handleEvent: (HomeEvent) -> Unit,
    isConnectionErrorVisible: Boolean = false,
    onConnectionErrorDismissed: () -> Unit = {},
    isCountriesErrorVisible: Boolean = false,
    onCountriesErrorDismissed: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSettingsPlaceholderVisible by rememberSaveable { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(VpnColors.Background)
    ) {
        val layoutMetrics = rememberHomeLayoutMetrics(
            availableWidth = maxWidth,
            availableHeight = maxHeight
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(layoutMetrics.glowHeight)
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
            HomeTopBar(
                onSettingsClick = { isSettingsPlaceholderVisible = true }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = layoutMetrics.horizontalPadding)
                    .padding(bottom = layoutMetrics.contentBottomPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ConnectionSection(
                    vpnState = state.vpnState,
                    onOrbClick = { handleEvent(HomeEvent.OnConnectClicked) },
                    modifier = Modifier.padding(top = layoutMetrics.connectionTopPadding)
                )

                BottomSection(
                    state = state,
                    onSelectCountry = { handleEvent(HomeEvent.OnSelectCountryClicked) },
                    modifier = Modifier.padding(bottom = layoutMetrics.bottomSectionBottomPadding)
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

        if (isSettingsPlaceholderVisible) {
            AlertDialog(
                onDismissRequest = { isSettingsPlaceholderVisible = false },
                confirmButton = {
                    TextButton(onClick = { isSettingsPlaceholderVisible = false }) {
                        Text(text = stringResource(R.string.home_common_ok))
                    }
                },
                title = { Text(text = stringResource(R.string.home_settings)) },
                text = { Text(text = stringResource(R.string.home_settings_placeholder_message)) }
            )
        }

        if (isConnectionErrorVisible) {
            AlertDialog(
                onDismissRequest = onConnectionErrorDismissed,
                confirmButton = {
                    TextButton(onClick = onConnectionErrorDismissed) {
                        Text(text = stringResource(R.string.home_common_ok))
                    }
                },
                title = { Text(text = stringResource(R.string.home_connection_error_title)) },
                text = { Text(text = stringResource(R.string.home_connection_error_message)) }
            )
        }

        if (isCountriesErrorVisible) {
            AlertDialog(
                onDismissRequest = onCountriesErrorDismissed,
                confirmButton = {
                    TextButton(onClick = onCountriesErrorDismissed) {
                        Text(text = stringResource(R.string.home_common_ok))
                    }
                },
                title = { Text(text = stringResource(R.string.home_countries_error_title)) },
                text = { Text(text = stringResource(R.string.home_countries_error_message)) }
            )
        }
    }
}

@Composable
private fun rememberHomeLayoutMetrics(
    availableWidth: Dp,
    availableHeight: Dp
): HomeLayoutMetrics {
    val horizontalPadding = when {
        availableWidth < 360.dp -> 20.dp
        availableWidth < 600.dp -> 24.dp
        else -> 32.dp
    }
    val connectionTopPadding = when {
        availableHeight < 700.dp -> 24.dp
        availableHeight < 900.dp -> 40.dp
        else -> 56.dp
    }
    val contentBottomPadding = when {
        availableHeight < 700.dp -> 24.dp
        availableHeight < 900.dp -> 40.dp
        else -> 56.dp
    }
    val bottomSectionBottomPadding = when {
        availableHeight < 700.dp -> 8.dp
        availableHeight < 900.dp -> 20.dp
        else -> 28.dp
    }
    val glowHeight = (availableHeight * 0.34f).coerceIn(220.dp, 360.dp)

    return HomeLayoutMetrics(
        horizontalPadding = horizontalPadding,
        connectionTopPadding = connectionTopPadding,
        contentBottomPadding = contentBottomPadding,
        bottomSectionBottomPadding = bottomSectionBottomPadding,
        glowHeight = glowHeight
    )
}

private data class HomeLayoutMetrics(
    val horizontalPadding: Dp,
    val connectionTopPadding: Dp,
    val contentBottomPadding: Dp,
    val bottomSectionBottomPadding: Dp,
    val glowHeight: Dp
)

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
