package com.org.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.home.mappers.toDomain
import com.org.home.model.VpnState
import com.org.home.use_case.GetAllCountriesUseCase
import com.org.vpn.service.MockVpnService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val mockVpnService: MockVpnService
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private var vpnJob: Job? = null

    init {
        getCountries()
    }

    internal fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnSelectCountryClicked -> handleOnSelectCountryClicked()
            HomeEvent.OnConnectClicked -> handleOnConnectClicked()
            HomeEvent.OnBottomSheetDismissed -> dismissBottomSheet()
            is HomeEvent.OnCountrySelected -> handleOnCountrySelected(event)
            is HomeEvent.OnSearchQueryChanged -> _state.update { it.copy(searchQuery = event.query) }
        }
    }

    private fun handleOnConnectClicked() {
        val current = _state.value.vpnState
        if (current == VpnState.CONNECTED || current == VpnState.CONNECTING) {
            vpnJob?.cancel()
            _state.update { it.copy(vpnState = VpnState.DISCONNECTED) }
            return
        }

        val country = _state.value.selectedCountry?.countryName.orEmpty()
        vpnJob?.cancel()
        vpnJob = viewModelScope.launch {
            mockVpnService.startConnection(country).collect { serviceState ->
                _state.update { it.copy(vpnState = serviceState.toDomain()) }
            }
        }
    }

    private fun handleOnSelectCountryClicked() {
        _state.update { it.copy(isBottomSheetVisible = true) }
    }

    private fun handleOnCountrySelected(event: HomeEvent.OnCountrySelected) {
        _state.update {
            it.copy(
                selectedCountry = event.country,
                isBottomSheetVisible = false,
                searchQuery = ""
            )
        }
    }

    private fun dismissBottomSheet() {
        _state.update { it.copy(isBottomSheetVisible = false, searchQuery = "") }
    }

    private fun getCountries() {
        viewModelScope.launch {
            runCatching {
                getAllCountriesUseCase()
            }.onSuccess { countries ->
                _state.update {
                    it.copy(
                        countries = countries,
                        selectedCountry = countries.firstOrNull()
                    )
                }
            }.onFailure { println("HomeViewModel: error = ${it.message}") }
        }
    }
}
