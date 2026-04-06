package com.org.home.ui

import com.org.home.model.CountryDomain

sealed interface HomeEvent {
    data object OnSelectCountryClicked : HomeEvent
    data object OnConnectClicked : HomeEvent
    data object OnBottomSheetDismissed : HomeEvent
    data class OnCountrySelected(val country: CountryDomain) : HomeEvent
    data class OnSearchQueryChanged(val query: String) : HomeEvent
}