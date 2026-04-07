package com.org.vpn.service

import com.org.vpn.model.VpnState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MockVpnService @Inject constructor() {

    fun startConnection(country: String): Flow<VpnState> = flow {
        emit(VpnState.CONNECTING)
        delay(DELAY_TWO_SECONDS)

        if (country.isBlank()) {
            throw IllegalStateException("Connection failed")
        }

        emit(VpnState.CONNECTED)
    }.flowOn(Dispatchers.IO)

    private companion object {

        const val DELAY_TWO_SECONDS = 2_000L
    }
}
