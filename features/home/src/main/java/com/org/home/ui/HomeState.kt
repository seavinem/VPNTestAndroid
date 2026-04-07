package com.org.home.ui

import androidx.compose.runtime.Immutable
import com.org.home.model.CountryDomain
import com.org.vpn.model.VpnState

@Immutable
data class HomeState(
    val isLoading: Boolean = false,
    val isBottomSheetVisible: Boolean = false,
    val vpnState: VpnState = VpnState.DISCONNECTED,
    val selectedCountry: CountryDomain? = null,
    val countries: List<CountryDomain> = emptyList(),
    val searchQuery: String = ""
)
